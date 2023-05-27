<template>
<div style="width: 200px; padding-left: 30px; color: green; font-weight: bold; font-size: 20px;">已完成</div>
  <div class="home" style="padding: 10px">
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable />
      <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>
    </div>

    <!--    表格区域-->
    <el-table v-loading="loading" :data="tableData" border stripe table-layout="auto" style="width: 100%">
      <el-table-column prop="houseId" label="户号" width="110" />
      <el-table-column prop="addApply" label="1.并网申请" width="170" />
      <el-table-column prop="addSurvey" label="2.现场勘查" width="170" />
      <el-table-column prop="addConfirm" label="3.接入方案设计及确认" width="170" />
      <el-table-column prop="addRecord" label="4.项目备案" width="170" />
      <el-table-column prop="connectApply" label="5.并网验收与调试申请" width="170" />
      <el-table-column prop="connectDevice" label="6.安装计量装置" width="170" />
      <el-table-column prop="connectSign" label="7.签署合同及协议" width="170" />
      <el-table-column prop="connectCheck" label="8.并网验收与调试" width="170" />
      <el-table-column prop="isComplete" label="是否并网运行" width="100" />
      <el-table-column prop="notes" label="备注" width="50" />

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-popconfirm title="确定删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页区域-->
    <div style="text-align: center">
      <el-pagination v-model:currentPage="currentPage" v-model:page-size="pageSize" :page-sizes="[50, 100, 200, 500]"
        layout="total, sizes, prev, pager, next, jumper" :background="true" :total="total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    </div>
  </div>
</template>

<script >
import { ElMessage } from "element-plus";
import request from "@/utils/request";

export default {
  methods: {

    clear() {
      this.$refs['form1'].resetFields()
      this.dialogVisible = false
    },
    handleEdit(id) {
      this.$router.push({
        name: "detail",
        query: {
          id: id
        }
      })
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/grid-connect/" + id).then(res => {
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
      this.load()
    },
    load() {
      //console.log("数据加载")
      this.loading = true
      request.get("/grid-connect", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          criteria: 1,
        }
      }).then(res => {
        this.loading = false
        for (let r in res.data.records) {
          if (res.data.records[r].isComplete === 0) {
            res.data.records[r].isComplete = "否"
          } else {
            res.data.records[r].isComplete = "是"
          }
        }
        this.tableData = res.data.records
        this.total = res.data.total
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

  },
  data() {
    return {
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      reason: {},
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