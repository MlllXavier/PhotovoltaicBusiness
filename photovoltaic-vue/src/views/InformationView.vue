<template>
  <div class="home" style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable />
      <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>
    </div>

    <!--    表格区域-->
    <el-table v-loading="loading" :data="tableData" stripe table-layout="auto" style="width: 100%">
      <el-table-column prop="houseId" label="户号" width="110" sortable />
      <el-table-column prop="houseName" label="户名" width="100" />
      <el-table-column prop="houseAddress" label="地址" width="100" />
      <el-table-column prop="houseOwner" label="房屋产权人" width="100" />
      <el-table-column prop="contactName" label="联系人" width="100" />
      <el-table-column prop="contactNumber" label="联系电话" width="120" />
      <el-table-column prop="addReason" label="新建用户原因" width="80" />
      <el-table-column prop="houseType" label="安装处房屋情况" width="80" />
      <el-table-column prop="totalFloor" label="总楼层" width="50" />
      <el-table-column prop="houseFloor" label="居住楼层" width="50" />
      <el-table-column prop="installationType" label="安装方式" width="100" />
      <el-table-column prop="powerSupply" label="供电电压" width="100" />
      <el-table-column prop="internetCapacity" label="光伏上网容量(kw)" width="60" />
      <el-table-column prop="connectionTime" label="光伏并网时间" width="115" />
      <el-table-column prop="poweredDevices" label="主要用电设备" width="100" />
      <el-table-column prop="consumptionMode" label="发电量消纳方式" width="100" />
      <el-table-column prop="notes" label="备注" width="100" />
      <el-table-column fixed="right" label="操作" width="140">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
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

      <!--      新增数据对话框-->
      <el-dialog v-model="dialogVisible" title="新增用户" width="50%" :before-close="clear">
        <el-form :model="form" ref="form1" label-width="160px" :rules="reason">
          <el-form-item label="1.户号" prop="houseId">
            <el-input v-model="form.houseId" style="width: 80%" />
          </el-form-item>
          <el-form-item label="2.户名" prop="houseName">
            <el-input v-model="form.houseName" style="width: 80%" />
          </el-form-item>
          <el-form-item label="3.地址" prop="houseAddress">
            <el-input v-model="form.houseAddress" style="width: 80%" />
          </el-form-item>
          <el-form-item label="4.房屋产权人" prop="houseOwner">
            <el-input v-model="form.houseOwner" style="width: 80%" />
          </el-form-item>
          <el-form-item label="5.联系人" prop="contactName">
            <el-input v-model="form.contactName" style="width: 80%" />
          </el-form-item>
          <el-form-item label="6.联系电话" prop="contactNumber">
            <el-input v-model="form.contactNumber" style="width: 80%" />
          </el-form-item>
          <el-form-item label="7.新建用户原因" prop="addReason" @change="changeReason">
            <el-radio-group v-model="form.addReason" class="ml-4">
              <el-radio label="新开户" size="large">新开户</el-radio>
              <el-radio label="原用户补录" size="large">原用户补录</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="8.安装处房屋情况" prop="houseType">
            <el-radio-group v-model="form.houseType" class="ml-4">
              <el-radio label="独立" size="large">独立</el-radio>
              <el-radio label="联体" size="large">联体</el-radio>
              <el-radio label="联排" size="large">联排</el-radio>
              <el-radio label="高层" size="large">高层</el-radio>
              <el-radio label="小高层" size="large">小高层</el-radio>
              <el-radio label="多层" size="large">多层</el-radio>
              <el-radio label="私房" size="large">私房</el-radio>
              <el-radio label="公租房" size="large">公租房</el-radio>
              <el-radio label="其他" size="large">其他</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="9.总楼层" prop="totalFloor">
            <el-input v-model="form.totalFloor" style="width: 80%" />
          </el-form-item>
          <el-form-item label="10.居住楼层" prop="houseFloor">
            <el-input v-model="form.houseFloor" style="width: 80%" />
          </el-form-item>
          <el-form-item label="11.安装方式" prop="installationType">
            <el-radio-group v-model="form.installationType" class="ml-4">
              <el-radio label="安装在房屋顶层" size="large">安装在房屋顶层</el-radio>
              <el-radio label="安装在家庭阳台/独立面" size="large">安装在家庭阳台/独立面</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="12.供电电压" prop="powerSupply">
            <el-radio-group v-model="form.powerSupply" class="ml-4">

              <el-radio label="220V单相" size="large">220V单相</el-radio>
              <el-radio label="380V三相" size="large">380V三相</el-radio>
              <el-radio label="其他" size="large">其他</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="13.光伏上网容量(kw)" prop="internetCapacity">
            <el-input v-model="form.internetCapacity" style="width: 80%" />
          </el-form-item>
          <el-form-item label="14.光伏并网时间" prop="connectionTime">
            <el-config-provider :locale="zhCn">
              <el-date-picker v-model="form.connectionTime" type="datetime"></el-date-picker>
            </el-config-provider>
          </el-form-item>
          <el-form-item label="15.主要用电设备" prop="poweredDevices">
            <el-input v-model="form.poweredDevices" style="width: 80%" />
          </el-form-item>
          <el-form-item label="16.发电量消纳方式" prop="consumptionMode">
            <el-radio-group v-model="form.consumptionMode" class="ml-4">
              <el-radio label="全部自用" size="large">全部自用</el-radio>
              <el-radio label="自发自用" size="large">自发自用</el-radio>
              <el-radio label="余电上网" size="large">余电上网</el-radio>
              <el-radio label="全部上网" size="large">全部上网</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="17.备注" prop="notes">
            <el-input v-model="form.notes" style="width: 80%" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script >
import { ElMessage } from "element-plus";
import request from "@/utils/request";
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
  methods: {
    resetForm() {
      this.form.id = null
      this.form.houseId = null
      this.form.houseName = null
      this.form.houseAddress = null
      this.form.houseOwner = null
      this.form.contactName = null
      this.form.contactNumber = null
      this.form.addReason = null
      this.form.houseType = null
      this.form.totalFloor = null
      this.form.houseFloor = null
      this.form.installationType = null
      this.form.powerSupply = null
      this.form.internetCapacity = null
      this.form.connectionTime = null
      this.form.poweredDevices = null
      this.form.consumptionMode = null
      this.form.notes = null
    },
    clear() {
      this.$refs['form1'].resetFields()
      this.dialogVisible = false
    },
    add() {
      try {
        this.$refs['form1'].clearValidate()
      } catch (e) {
        console.log(e)
      }
      this.dialogVisible = true
      this.resetForm()
    },
    handleEdit(row) {
      this.form.id = row.id
      this.form.houseId = row.houseId
      this.form.houseName = row.houseName
      this.form.houseAddress = row.houseAddress
      this.form.houseOwner = row.houseOwner
      this.form.contactName = row.contactName
      this.form.contactNumber = row.contactNumber
      this.form.addReason = row.addReason
      this.form.houseType = row.houseType
      this.form.totalFloor = row.totalFloor
      this.form.houseFloor = row.houseFloor
      this.form.installationType = row.installationType
      this.form.powerSupply = row.powerSupply
      this.form.internetCapacity = row.internetCapacity
      this.form.connectionTime = row.connectionTime
      this.form.poweredDevices = row.poweredDevices
      this.form.consumptionMode = row.consumptionMode
      this.form.notes = row.notes
      this.dialogVisible = true
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/house-info/" + id).then(res => {
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
      request.get("/house-info", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.loading = false
        for (let r in res.data.records) {
          if (res.data.records[r].addReason === 0) {
            res.data.records[r].addReason = "新开户"
          } else {
            res.data.records[r].addReason = "原用户补录"
          }
          if (res.data.records[r].houseType === 0) {
            res.data.records[r].houseType = "独立"
          } else if (res.data.records[r].houseType === 1) {
            res.data.records[r].houseType = "联体"
          } else if (res.data.records[r].houseType === 2) {
            res.data.records[r].houseType = "联排"
          } else if (res.data.records[r].houseType === 3) {
            res.data.records[r].houseType = "高层"
          } else if (res.data.records[r].houseType === 4) {
            res.data.records[r].houseType = "小高层"
          } else if (res.data.records[r].houseType === 5) {
            res.data.records[r].houseType = "多层"
          } else if (res.data.records[r].houseType === 6) {
            res.data.records[r].houseType = "私房"
          } else if (res.data.records[r].houseType === 7) {
            res.data.records[r].houseType = "公租房"
          } else if (res.data.records[r].houseType === 8) {
            res.data.records[r].houseType = "其他"
          }
          if (res.data.records[r].installationType === 0) {
            res.data.records[r].installationType = "安装在房屋顶层"
          } else if (res.data.records[r].installationType === 1) {
            res.data.records[r].installationType = "安装在家庭阳台/独立面"
          }
          if (res.data.records[r].consumptionMode === 0) {
            res.data.records[r].consumptionMode = "全部自用"
          } else if (res.data.records[r].consumptionMode === 1) {
            res.data.records[r].consumptionMode = "自发自用"
          } else if (res.data.records[r].consumptionMode === 2) {
            res.data.records[r].consumptionMode = "余电上网"
          } else if (res.data.records[r].consumptionMode === 3) {
            res.data.records[r].consumptionMode = "全部上网"
          }
          if (res.data.records[r].powerSupply === 0) {
            res.data.records[r].powerSupply = "220V单相"
          } else if (res.data.records[r].powerSupply === 1) {
            res.data.records[r].powerSupply = "380V三相"
          } else if (res.data.records[r].powerSupply === 2) {
            res.data.records[r].powerSupply = "其他"
          }
        }
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {
      if (this.form.addReason === null) {
        ElMessage({
          message: "新增原因不能为空",
          type: "warning"
        })
        return
      } else if (this.form.addReason === '原用户补录') {
        this.$refs['form1'].validate((valid, fields) => {
          if (!valid) {
            ElMessage({
              message: "该用户信息不合规:1-16项为必填项",
              type: "warning"
            })
          } else {
            if (this.form.id != null) {  //更新
              if (this.form.addReason === "新开户") {
                this.form.addReason = 0
              } else {
                this.form.addReason = 1
              }
              if (this.form.houseType === "独立") {
                this.form.houseType = 0
              } else if (this.form.houseType === "联体") {
                this.form.houseType = 1
              } else if (this.form.houseType === "联排") {
                this.form.houseType = 2
              } else if (this.form.houseType === "高层") {
                this.form.houseType = 3
              } else if (this.form.houseType === "小高层") {
                this.form.houseType = 4
              } else if (this.form.houseType === "多层") {
                this.form.houseType = 5
              } else if (this.form.houseType === "私房") {
                this.form.houseType = 6
              } else if (this.form.houseType === "公租房") {
                this.form.houseType = 7
              } else if (this.form.houseType === "其他") {
                this.form.houseType = 8
              }
              if (this.form.installationType === "安装在房屋顶层") {
                this.form.installationType = 0
              } else {
                this.form.installationType = 1
              }
              if (this.form.powerSupply === "220V单相") {
                this.form.powerSupply = 0
              } else if (this.form.powerSupply === "380V三相") {
                this.form.powerSupply = 1
              } else if (this.form.powerSupply === "其他") {
                this.form.powerSupply = 2
              }
              if (this.form.consumptionMode === "全部自用") {
                this.form.consumptionMode = 0
              } else if (this.form.consumptionMode === "自发自用") {
                this.form.consumptionMode = 1
              } else if (this.form.consumptionMode === "余电上网") {
                this.form.consumptionMode = 2
              } else if (this.form.consumptionMode === "全部上网") {
                this.form.consumptionMode = 3
              }
              request.put("/house-info", this.form).then(res => {
                console.log(res)
                if (res) {
                  ElMessage({
                    message: "更新成功",
                    type: "success"
                  })
                } else {
                  ElMessage({
                    message: "更新失败",
                    type: "error"
                  })
                }
              })
            } else {  //新增
              if (this.form.addReason === "新开户") {
                this.form.addReason = 0
              } else {
                this.form.addReason = 1
              }
              if (this.form.houseType === "独立") {
                this.form.houseType = 0
              } else if (this.form.houseType === "联体") {
                this.form.houseType = 1
              } else if (this.form.houseType === "联排") {
                this.form.houseType = 2
              } else if (this.form.houseType === "高层") {
                this.form.houseType = 3
              } else if (this.form.houseType === "小高层") {
                this.form.houseType = 4
              } else if (this.form.houseType === "多层") {
                this.form.houseType = 5
              } else if (this.form.houseType === "私房") {
                this.form.houseType = 6
              } else if (this.form.houseType === "公租房") {
                this.form.houseType = 7
              } else if (this.form.houseType === "其他") {
                this.form.houseType = 8
              }
              if (this.form.installationType === "安装在房屋顶层") {
                this.form.installationType = 0
              } else {
                this.form.installationType = 1
              }
              if (this.form.powerSupply === "220V单相") {
                this.form.powerSupply = 0
              } else if (this.form.powerSupply === "380V三相") {
                this.form.powerSupply = 1
              } else if (this.form.powerSupply === "其他") {
                this.form.powerSupply = 2
              }
              if (this.form.consumptionMode === "全部自用") {
                this.form.consumptionMode = 0
              } else if (this.form.consumptionMode === "自发自用") {
                this.form.consumptionMode = 1
              } else if (this.form.consumptionMode === "余电上网") {
                this.form.consumptionMode = 2
              } else if (this.form.consumptionMode === "全部上网") {
                this.form.consumptionMode = 3
              }
              request.post("/house-info", this.form).then(res => {
                if (res) {
                  ElMessage({
                    message: "新增成功",
                    type: "success"
                  })
                } else {
                  ElMessage({
                    message: "新增失败",
                    type: "error"
                  })
                }
              })
            }
            this.dialogVisible = false
            this.load()
          }
        })
      } else if (this.form.addReason === '新开户') {
        console.log("新开户")
        this.$refs['form1'].validate((valid, fields) => {
          console.log(this.form.id)
          if (this.form.id != null) {  //更新
            console.log("Update")
            if (this.form.addReason === "新开户") {
              this.form.addReason = 0
            } else {
              this.form.addReason = 1
            }
            if (this.form.houseType === "独立") {
              this.form.houseType = 0
            } else if (this.form.houseType === "联体") {
              this.form.houseType = 1
            } else if (this.form.houseType === "联排") {
              this.form.houseType = 2
            } else if (this.form.houseType === "高层") {
              this.form.houseType = 3
            } else if (this.form.houseType === "小高层") {
              this.form.houseType = 4
            } else if (this.form.houseType === "多层") {
              this.form.houseType = 5
            } else if (this.form.houseType === "私房") {
              this.form.houseType = 6
            } else if (this.form.houseType === "公租房") {
              this.form.houseType = 7
            } else if (this.form.houseType === "其他") {
              this.form.houseType = 8
            }
            if (this.form.installationType === "安装在房屋顶层") {
              this.form.installationType = 0
            } else {
              this.form.installationType = 1
            }
            if (this.form.powerSupply === "220V单相") {
              this.form.powerSupply = 0
            } else if (this.form.powerSupply === "380V三相") {
              this.form.powerSupply = 1
            } else if (this.form.powerSupply === "其他") {
              this.form.powerSupply = 2
            }
            if (this.form.consumptionMode === "全部自用") {
              this.form.consumptionMode = 0
            } else if (this.form.consumptionMode === "自发自用") {
              this.form.consumptionMode = 1
            } else if (this.form.consumptionMode === "余电上网") {
              this.form.consumptionMode = 2
            } else if (this.form.consumptionMode === "全部上网") {
              this.form.consumptionMode = 3
            }
            request.put("/house-info", this.form).then(res => {

              if (res) {
                ElMessage({
                  message: "更新成功",
                  type: "success"
                })
              } else {
                ElMessage({
                  message: "更新失败",
                  type: "error"
                })
              }
            })
          } else {  //新增

            if (this.form.addReason === "新开户") {
              this.form.addReason = 0
            } else {
              this.form.addReason = 1
            }
            if (this.form.houseType === "独立") {
              this.form.houseType = 0
            } else if (this.form.houseType === "联体") {
              this.form.houseType = 1
            } else if (this.form.houseType === "联排") {
              this.form.houseType = 2
            } else if (this.form.houseType === "高层") {
              this.form.houseType = 3
            } else if (this.form.houseType === "小高层") {
              this.form.houseType = 4
            } else if (this.form.houseType === "多层") {
              this.form.houseType = 5
            } else if (this.form.houseType === "私房") {
              this.form.houseType = 6
            } else if (this.form.houseType === "公租房") {
              this.form.houseType = 7
            } else if (this.form.houseType === "其他") {
              this.form.houseType = 8
            }
            if (this.form.installationType === "安装在房屋顶层") {
              this.form.installationType = 0
            } else if (this.form.installationType === "安装在家庭阳台/独立面") {
              this.form.installationType = 1
            }
            if (this.form.powerSupply === "220V单相") {
              this.form.powerSupply = 0
            } else if (this.form.powerSupply === "380V三相") {
              this.form.powerSupply = 1
            } else if (this.form.powerSupply === "其他") {
              this.form.powerSupply = 2
            }
            if (this.form.consumptionMode === "全部自用") {
              this.form.consumptionMode = 0
            } else if (this.form.consumptionMode === "自发自用") {
              this.form.consumptionMode = 1
            } else if (this.form.consumptionMode === "余电上网") {
              this.form.consumptionMode = 2
            } else if (this.form.consumptionMode === "全部上网") {
              this.form.consumptionMode = 3
            }

            request.post("/house-info", this.form).then(res => {
              if (res) {

                ElMessage({
                  message: "新增成功",
                  type: "success"
                })
              } else {
                ElMessage({
                  message: "新增失败",
                  type: "error"
                })
              }
            })
          }
          this.dialogVisible = false
          location.reload()
        }
        )
      } else {
        ElMessage({
          message: "请重新选择新增原因",
          type: "warning"
        })
      }

    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
    changeReason() {
      if (this.form.addReason === '原用户补录') {
        this.reason = this.rules1
      } else {
        this.reason = this.rules2
      }
      this.$refs['form1'].clearValidate()
    }

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
      rules1: {
        houseId: [
          { required: true, message: '请输入户号', trigger: 'blur' },
          { min: 10, max: 10, message: '长度为10', trigger: 'blur' },
        ],
        houseName: [
          { required: true, message: '请输入户名', trigger: 'blur' },
        ],
        houseAddress: [
          { required: true, message: '请输入地址', trigger: 'blur' },
        ],
        houseOwner: [
          { required: true, message: '请输入房屋产权人', trigger: 'blur' },
        ],
        contactName: [
          { required: true, message: '请输入联系人', trigger: 'blur' },
        ],
        contactNumebr: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        addReason: [
          { required: true, message: '请选择新建用户原因', trigger: 'change' },
        ],
        houseType: [
          { required: true, message: '请选择安装处房屋情况', trigger: 'change' },
        ],
        totalFloor: [
          { required: true, message: '请输入总楼层', trigger: 'blur' },
        ],
        houseFloor: [
          { required: true, message: '请输入居住楼层', trigger: 'blur' },
        ],
        installationType: [
          { required: true, message: '请选择安装方式', trigger: 'change' },
        ],
        powerSupply: [
          { required: true, message: '请选择供电电压', trigger: 'change' },
        ],
        internetCapacity: [
          { required: true, message: '请输入光伏上网容量(kw)', trigger: 'blur' },
        ],
        connectionTime: [
          { required: true, message: '请选择光伏并网时间', trigger: 'change' },
        ],
        poweredDevices: [
          { required: true, message: '请输入主要用电设备', trigger: 'blur' },
        ],
        consumptionMode: [
          { required: true, message: '请选择发电量消纳方式', trigger: 'change' },
        ]
      },
      rules2: {
        houseId: [
          { required: true, message: '请输入户号', trigger: 'blur' },
          { min: 10, max: 10, message: '长度为10', trigger: 'blur' },
        ],
        houseName: [
          { required: true, message: '请输入户名', trigger: 'blur' },
        ],
        houseAddress: [
          { required: true, message: '请输入地址', trigger: 'blur' },
        ],
        houseOwner: [
          { required: true, message: '请输入房屋产权人', trigger: 'blur' },
        ],
        contactName: [
          { required: true, message: '请输入联系人', trigger: 'blur' },
        ],
        contactNumber: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        addReason: [
          { required: true, message: '请选择新建用户原因', trigger: 'change' },
        ],
      }
    }
  },
  created() {
    this.load()
    this.reason = this.rules1
  },
}

</script>