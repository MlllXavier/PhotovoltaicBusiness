<template>
    <div class="compensation" style="padding: 10px">
        <el-row :gutter="20">
            <el-col :span="1">
                <el-button type="primary" @click="add">新增</el-button>
            </el-col>
            <el-col :span="1">
                <el-button type="primary" @click="addSubsidy">追退补</el-button>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="4">
                <el-input v-model="search.text" placeholder="请输入户号或姓名" clearable />
            </el-col>
            <el-col :span="3">
                <el-config-provider :locale="zhCn">
                    <el-date-picker v-model="search.startTime" type="month" placeholder="选择开始日期"></el-date-picker>
                </el-config-provider>
            </el-col>
            <el-col :span="3">
                <el-config-provider :locale="zhCn">
                    <el-date-picker v-model="search.endTime" type="month" placeholder="选择结束日期"></el-date-picker>
                </el-config-provider>
            </el-col>
            <el-col :span="1">
                <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>
            </el-col>
        </el-row>
        <!--    表格区域-->
        <el-table v-loading="loading" :data="tableData" stripe style="width: 100%" show-summary
            :table-layout="tableLayout">
            <el-table-column prop="subsidyId" label="追退补编号" sortable />
            <el-table-column prop="houseId" label="户号" sortable />
            <el-table-column prop="houseName" label="户名" />
            <el-table-column prop="timeBegin" label="开始日期" :formatter="formatDate" />
            <el-table-column prop="timeFinish" label="结束日期" :formatter="formatDate" />
            <el-table-column prop="errorFee" label="错误单价" />
            <el-table-column prop="rightFee" label="正确单价" />
            <el-table-column prop="generatingQuantity" label="总发电量" />
            <el-table-column prop="compensationFee" label="总需要追补金额" />
            <el-table-column prop="subsidyTime" label="追补日期" :formatter="formatDate" />
            <el-table-column prop="subsidyFee" label="追补金额" />
            <el-table-column prop="subsidyTotalFee" label="累计已追补金额" />
            <el-table-column prop="subsidyRestFee" label="还需追补金额" />
            <el-table-column prop="notes" label="备注" />
            <el-table-column prop="" label="操作">
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
            <el-pagination v-model:currentPage="currentPage" v-model:page-size="pageSize"
                :page-sizes="[50, 100, 200, 500]" layout="total, sizes, prev, pager, next, jumper" :background="true"
                :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />

            <!--      新增数据对话框-->
            <el-dialog v-model="dialogVisible" title="新增追退补记录" width="50%" :before-close="clear">
                <el-form :model="form" ref="form1" label-width="120px" :rules="rules1">
                    <el-form-item label="1.户号" prop="houseId">
                        <el-input v-model="form.houseId" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="2.开始日期" prop="timeBegin">
                        <el-config-provider :locale="zhCn">
                            <el-date-picker v-model="form.timeBegin" type="month"></el-date-picker>
                        </el-config-provider>
                    </el-form-item>
                    <el-form-item label="3.结束日期" prop="timeFinish">
                        <el-date-picker v-model="form.timeFinish" type="month"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="4.正确单价" prop="rightFee">
                        <el-input v-model="form.rightFee" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="5.备注" prop="notes">
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

            <el-dialog v-model="dialogVisible2" title="新增台账" width="50%" :before-close="clear">
                <el-form :model="form" ref="form2" label-width="120px" :rules="rules2">
                    <el-form-item label="1.追退补编号" prop="subsidyId">
                        <el-input v-model="form.subsidyId" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="2.追退补日期" prop="subsidyTime">
                        <el-config-provider :locale="zhCn">
                            <el-date-picker v-model="form.subsidyTime" type="month"></el-date-picker>
                        </el-config-provider>
                    </el-form-item>
                    <el-form-item label="3.该月追退补费用" prop="subsidyFee">
                        <el-input v-model="form.subsidyFee" style="width: 80%" />
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogVisible2 = false">取消</el-button>
                        <el-button type="primary" @click="save2">确定</el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script >
import { ElMessage } from "element-plus";
import request from "@/utils/request";
import { auto } from "@popperjs/core";
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
    methods: {
        formatDate: function (row, column) {
            let data = row[column.property]
            if (data == null) {
                return null
            }
            let date = new Date(data);
            var o = {
                "M+": date.getMonth() + 1,                 //月份 
                "d+": date.getDate(),                    //日 
                "h+": date.getHours(),                   //小时 
                "m+": date.getMinutes(),                 //分 
                "s+": date.getSeconds(),                 //秒 
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度 
                "S": date.getMilliseconds()             //毫秒 
            };
            //var fmt = "yyyy-MM-dd hh:mm:ss";
            var fmt = "yyyy-MM";
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        },
        resetForm() {
            this.form.id = null
            this.form.subsidyId = null
            this.form.houseId = null
            this.form.houseName = null
            this.form.timeBegin = null
            this.form.timeFinish = null
            this.form.errorFee = null
            this.form.rightFee = null
            this.form.generatingQuantity = null
            this.form.compensationFee = null
            this.form.subsidyTime = null
            this.form.subsidyFee = null
            this.form.subsidyTotalFee = null
            this.form.subsidyRestFee = null
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
                //console.log(e)
            }
            this.dialogVisible = true
            this.resetForm()
        },
        addSubsidy() {
            try {
                this.$refs['form2'].clearValidate()
            } catch (e) {
                //console.log(e)
            }
            this.dialogVisible2 = true
            this.resetForm()
        },
        handleEdit(row) {
            this.form.id = row.id
            this.form.subsidyId = row.subsidyId
            this.form.houseId = row.houseId
            this.form.houseName = row.houseName
            this.form.timeBegin = row.timeBegin
            this.form.timeFinish = row.timeFinish
            this.form.errorFee = row.errorFee
            this.form.rightFee = row.rightFee
            this.form.generatingQuantity = row.generatingQuantity
            this.form.compensationFee = row.compensationFee
            this.form.subsidyTime = row.subsidyTime
            this.form.subsidyFee = row.subsidyFee
            this.form.subsidyTotalFee = row.subsidyTotalFee
            this.form.subsidyRestFee = row.subsidyRestFee
            this.form.notes = row.notes
            this.dialogVisible = true
        },
        handleDelete(id) {
            console.log(id)
            request.delete("/compensation/" + id).then(res => {
                if (res.code === 1000) {
                    ElMessage({
                        message: "删除成功",
                        type: "success"
                    })
                    this.load()
                } else {
                    ElMessage({
                        message: "删除失败",
                        type: "error"
                    })
                }
            })
        },
        load() {
            this.loading = true
            request.get("/compensation", {
                params: {
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    searchText: this.search.text,
                    searchStartTime: this.search.startTime,
                    searchEndTime: this.search.endTime
                }
            }).then(res => {
                this.loading = false
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        save() {
            if (this.form.id != null) {  //更新
                request.put("/compensation", this.form).then(res => {
                    if (res.code === 1000) {
                        ElMessage({
                            message: "更新成功",
                            type: "success"
                        })
                        this.load()
                    } else {
                        ElMessage({
                            message: "更新失败",
                            type: "error"
                        })
                    }
                })
            }
            else {  //新增
                request.post("/compensation", this.form).then(res => {
                    console.log(res)
                    if (res.code === 1000) {
                        ElMessage({
                            message: "新增成功",
                            type: "success"
                        })
                        this.load()
                    } else {
                        ElMessage({
                            message: "新增失败",
                            type: "error"
                        })
                    }
                })
            }
            this.dialogVisible = false
        },
        save2() {
            if (this.form.id != null) {  //更新
                request.put("/compensation", this.form).then(res => {
                    if (res.code === 1000) {
                        ElMessage({
                            message: "更新成功",
                            type: "success"
                        })
                        this.load()
                    } else {
                        ElMessage({
                            message: "更新失败",
                            type: "error"
                        })
                    }
                })
            }
            else {  //新增
                request.post("/compensation", this.form).then(res => {
                    console.log(res)
                    if (res.code === 1000) {
                        ElMessage({
                            message: "新增成功",
                            type: "success"
                        })
                        this.load()
                    } else {
                        ElMessage({
                            message: "新增失败",
                            type: "error"
                        })
                    }
                })
            }
            this.dialogVisible2 = false
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
            tableLayout: auto,
            loading: true,
            form: {},
            dialogVisible: false,
            dialogVisible2: false,
            search: {},
            currentPage: 1,
            pageSize: 100,
            total: 0,
            tableData: [],
            valueMonth: '',
            zhCn,
            rules1: {
                houseId: [
                    { required: true, message: '请输入户号', trigger: 'blur' },
                    { min: 10, max: 10, message: '长度为10', trigger: 'blur' },
                ],
                timeBegin: [
                    { required: true, message: '请输入开始日期', trigger: 'blur' },
                ],
                timeFinish: [
                    { required: true, message: '请输入结束日期', trigger: 'blur' },
                ],
                rightFee: [
                    { required: true, message: '请输入正确单价', trigger: 'blur' },
                ],
                notes: [
                    { required: false, message: '备注', trigger: 'blur' },
                ],
            },
            rules2: {
                subsidyId: [
                    { required: true, message: '请输入追退补编号', trigger: 'blur' },
                ],
                subsidyTime: [
                    { required: true, message: '请输入追退补日期', trigger: 'blur' },
                ],
                subsidyFee: [
                    { required: true, message: '请输入此月追退补费用', trigger: 'blur' },
                ],
            },
        }
    },
    created() {
        this.load()
    },
}

</script>

<style>
.el-row {
    margin-bottom: 20px;
}

.el-col {
    border-radius: 4px;
}
</style>