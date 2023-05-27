<template>
    <div class="subsidy" style="padding: 10px">
        <el-row :gutter="20">
            <el-col :span="1">
                <el-button type="primary" @click="add">新增</el-button>
            </el-col>
            <el-col :span="1">
                <el-button type="danger" @click="exportExcel()">导出</el-button>
            </el-col>
            <el-col :span="1">
                <el-upload ref="upload" class="upload-demo" action="#" accept=".xlsx, .xls" :auto-upload="false"
                    :on-change="uploadFile" :show-file-list="false">
                    <el-button type="warning">导入</el-button>
                </el-upload>
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
            <el-table-column prop="houseId" label="项目编号" sortable />
            <el-table-column prop="houseName" label="项目名称" />
            <el-table-column prop="time" label="电费年月" :formatter="formatDate" />
            <el-table-column prop="subsidyStandard" label="补助标准" />
            <el-table-column prop="generatingQuantity" label="发电量" />
            <el-table-column prop="networkPrice" label="上网电价" />
            <el-table-column prop="networkQuantity" label="上网电量" />
            <el-table-column prop="payableAmount" label="应付购电费" />
            <el-table-column prop="subsidyAmount" label="中央补助金额" />
            <el-table-column prop="totalAmount" label="总应付金额" />
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
            <el-dialog v-model="dialogVisible" title="新增补贴记录" width="50%" :before-close="clear">
                <el-form :model="form" ref="form1" label-width="120px" :rules="reason">
                    <el-form-item label="1.项目编号" prop="houseId">
                        <el-input v-model="form.houseId" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="2.项目名称" prop="houseName">
                        <el-input v-model="form.houseName" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="3.电费年月" prop="time">
                        <el-config-provider :locale="zhCn">
                            <el-date-picker v-model="form.time" type="month"></el-date-picker>
                        </el-config-provider>
                    </el-form-item>
                    <el-form-item label="4.补助标准" prop="subsidyStandard">
                        <el-input v-model="form.subsidyStandard" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="5.发电量" prop="generatingQuantity">
                        <el-input v-model="form.generatingQuantity" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="6.上网电价" prop="networkPrice">
                        <el-input v-model="form.networkPrice" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="7.上网电量" prop="networkQuantity">
                        <el-input v-model="form.networkQuantity" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="8.应付购电费" prop="payableAmount">
                        <el-input v-model="form.payableAmount" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="9.中央补助金额" prop="subsidyAmount">
                        <el-input v-model="form.subsidyAmount" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="10.总应付金额" prop="totalAmount">
                        <el-input v-model="form.totalAmount" style="width: 80%" />
                    </el-form-item>
                    <el-form-item label="11.备注" prop="notes">
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

    <div style="width:100%;height:50px;text-align: left;">
        <p style="font-size:20px;color: dodgerblue;">补贴费用分析</p>
    </div>

    <el-row class="mb-4">
        <el-input style="width: 200px;margin: 0px 20px;" v-model="currHouseId" maxlength="10" placeholder="输入户号"
            show-word-limit type="text" />
        <el-input v-model="YEAR" placeholder="请输入年份" style="width: 120px;" />
        <el-button type="success" @click="getDataFromOneUser(currHouseId, YEAR)">分析</el-button>
    </el-row>
    <div id="echarts_box" style="height:400px;"></div>
    <div id="echarts_box2" style="height:400px;"></div>
</template>

<script >
import { ElMessage } from "element-plus";
import request from "@/utils/request";
import { auto } from "@popperjs/core";
import * as echarts from 'echarts';
import { exportExcels } from "@/api/exportExcel";
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
        uploadFile(item) {
            let formData = new FormData()
            let file = item.raw
            formData.append('file', file)
            const config = { headers: { 'Content-Type': 'multipart/form-data' } }
            request.post('/subsidy/import', formData, config
            ).then(response => {
                if (response.data == "SUCCESS") {
                    ElMessage({
                        message: "导入成功",
                        type: "success"
                    })
                    this.load();
                } else {
                    ElMessage({
                        message: "导入失败",
                        type: "error"
                    })
                }
            })
        },
        resetForm() {
            this.form.id = null
            this.form.houseId = null
            this.form.houseName = null
            this.form.time = null
            this.form.subsidyStandard = null
            this.form.generatingQuantity = null
            this.form.networkPrice = null
            this.form.networkQuantity = null
            this.form.payableAmount = null
            this.form.subsidyAmount = null
            this.form.totalAmount = null
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
            this.form.time = row.time
            this.form.subsidyStandard = row.subsidyStandard
            this.form.generatingQuantity = row.generatingQuantity
            this.form.networkPrice = row.networkPrice
            this.form.networkQuantity = row.networkQuantity
            this.form.payableAmount = row.payableAmount
            this.form.subsidyAmount = row.subsidyAmount
            this.form.totalAmount = row.totalAmount
            this.form.notes = row.notes
            this.dialogVisible = true
        },
        handleDelete(id) {
            console.log(id)
            request.delete("/subsidy/" + id).then(res => {
                if (res) {
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
            //console.log("数据加载")
            this.loading = true
            request.get("/subsidy", {
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
                request.put("/subsidy", this.form).then(res => {
                    console.log(res)
                    if (res) {
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
                request.post("/subsidy", this.form).then(res => {
                    if (res) {
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
        handleSizeChange(pageSize) {   // 改变当前每页的个数触发
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {  // 改变当前页码触发
            this.currentPage = pageNum
            this.load()
        },
        getDataFromOneUser(id, y) {
            request.get("/subsidy/getDataFromOneUser", {
                params: {
                    houseId: id,
                    yearIndex: y
                }
            }).then(res => {
                var chartDom = document.getElementById('echarts_box');
                var myChart = echarts.init(chartDom);
                var option;
                option = {
                    title: {
                        text: '用户分析数据'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['应付购电费', '中央补助金额', '总应付金额']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} 元'
                        }
                    },
                    series: [
                        {
                            name: '应付购电费',
                            type: 'line',
                            smooth: true,
                            data: res.data[2],
                        },
                        {
                            name: '中央补助金额',
                            type: 'line',
                            smooth: true,
                            data: res.data[3],
                        },
                        {
                            name: '总应付金额',
                            type: 'line',
                            smooth: true,
                            data: res.data[4],
                        },
                    ]
                };
                option && myChart.setOption(option);

                var chartDom2 = document.getElementById('echarts_box2');
                var myChart2 = echarts.init(chartDom2);
                var option2;
                option2 = {
                    title: {
                        text: '用户分析数据'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['发电量', '上网电量']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} kw'
                        }
                    },
                    series: [
                        {
                            name: '发电量',
                            type: 'line',
                            smooth: true,
                            data: res.data[0],
                        },
                        {
                            name: '上网电量',
                            type: 'line',
                            smooth: true,
                            data: res.data[1],
                        },
                    ]
                };
                option2 && myChart2.setOption(option2);
            })
        },
        exportExcel() {
            exportExcels().then()
        },
    },
    data() {
        return {
            tableLayout: auto,
            YEAR: "2022",
            currHouseId: '',
            loading: true,
            form: {},
            dialogVisible: false,
            search: {},
            currentPage: 1,
            reason: {},
            pageSize: 100,
            total: 0,
            tableData: [],
            rules1: {
                houseId: [
                    { required: true, message: '请输入项目编号', trigger: 'blur' },
                    { min: 10, max: 10, message: '长度为10', trigger: 'blur' },
                ],
                houseName: [
                    { required: true, message: '请输入项目名称', trigger: 'blur' },
                ],
                time: [
                    { required: true, message: '请输入电费年月', trigger: 'blur' },
                ],
                subsidyStandard: [
                    { required: true, message: '请输入补助标准', trigger: 'blur' },
                ],
                generatingQuantity: [
                    { required: true, message: '请输入发电量', trigger: 'blur' },
                ],
                networkPrice: [
                    { required: true, message: '请输入上网电价', trigger: 'blur' },
                ],
                networkQuantity: [
                    { required: true, message: '请输入上网电量', trigger: 'blur' },
                ],
                payableAmount: [
                    { required: true, message: '请输入应付购电费', trigger: 'blur' },
                ],
                subsidyAmount: [
                    { required: true, message: '请输入中央补助金额', trigger: 'blur' },
                ],
                totalAmount: [
                    { required: true, message: '请输入总应付金额', trigger: 'blur' },
                ],
                notes: [
                    { required: false, message: '备注', trigger: 'blur' },
                ],
            },
        }
    },
    created() {
        this.load()
        this.reason = this.rules1
    },
}

</script>

<script setup>
import { ArrowDown } from '@element-plus/icons-vue'
</script>

<style>
.el-row {
    margin-bottom: 20px;
}

.el-col {
    border-radius: 4px;
}
</style>