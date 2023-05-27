<template>
  <div class="home" style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">上传文件</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable />
      <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>
    </div>

    <!--    表格区域-->
    <el-table
        v-loading="loading"
        :data="tableData"
        stripe
        table-layout="auto"
        style="width: 100%">=
      <el-table-column prop="documentNumber" label="文件号" width="150" />
      <el-table-column prop="documentName" label="文件名称" width="300" />
      <el-table-column prop="documentTime" label="文件时间" width="200" />
      <el-table-column prop="notes" label="备注" width="100" />
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-link :href="'documents/' + scope.row.documentPath" type="primary" style="margin-bottom: 10px; margin-right: 10px; margin-top: 10px">
            查看</el-link>
          <el-popconfirm title="确定删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible1" title="政策文件上传" width="30%" style="text-align: center">
      <el-form :model="form" ref="form1" label-width="160px">
        <el-form-item label="1.文件号" prop="documentNumber">
          <el-input v-model="form.documentNumber" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="2.文件名称" prop="documentName">
          <el-input v-model="form.documentName" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="3.文件时间" prop="documentTime">
          <el-date-picker v-model="form.documentTime" value-format="YYYY-MM-DDTHH:mm:ss" type="datetime" style="width: 80%" clearable></el-date-picker>
        </el-form-item>
        <el-form-item label="4.备注" prop="notes">
          <el-input v-model="form.notes" type="textarea" :rows="6" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="5.文件上传" prop="process2">
          <el-upload
              :on-remove="removeFile"
              class="upload-demo" drag action="#" multiple :http-request="getFile"
                     style="height: 150px;width: 300px">
            <div class="el-upload__text">
              拖动文件或<em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
      </template>

    </el-dialog>



    <!--    分页区域-->
    <div style="text-align: center">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[50, 100, 200, 500]"
          layout="total, sizes, prev, pager, next, jumper"
          :background="true"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />


    </div>
  </div>
</template>

<script >
import {ElMessage} from "element-plus";
import request from "@/utils/request";

export default {
  methods: {
    resetForm() {
      this.form.documentNumber = null
      this.form.documentName = null
      this.form.documentTime = null
      this.form.notes = null
    },
    add() {
      this.dialogVisible1 = true
      this.resetForm()
    },
    removeFile() {
      this.file = null
    },
    handleDelete(id){
      request.delete("/documents/" + id).then(res => {
        if (res) {
          ElMessage({
            message: "删除成功",
            type: "success"
          })
        } else {
          ElMessage({
            message: "删除失败",
            type: "error"
          })
        }
      })
      location.reload()
    },
    load() {
      console.log("数据加载")
      this.loading = true
      request.get("/documents", {
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {
      if(this.form.documentNumber==null ||this.form.documentName==null || this.form.documentTime==null  || this.file==null ){
        ElMessage({
          message: "内容不得为空！",
          type: "error"
        })
      }
      const fd = new FormData()
      fd.append('file', this.file)
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' },
        params: {
          documentNumber: this.form.documentNumber,
          documentName: this.form.documentName,
          documentTime: this.form.documentTime,
          notes: this.form.notes
        }
      }
      request.post('/documents/add', fd, config
      ).then(response => {
        this.dialogVisible1 = false
        location.reload()
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
    getFile(item) {
      this.file = item.file
    },
  },
  data() {
    return {
      loading: true,
      form: {},
      dialogVisible1: false,
      dialogVisible2: false,
      search: '',
      currentPage: 1,
      file: {},
      pageSize: 100,
      total: 0,
      tableData: [],

    }
  },
  created() {
    this.load()
  },
}

</script>