<template>
  <div class="home" style="padding: 10px">
    <div class="demo-progress" style="width: 1000px">
      <el-progress :text-inside="true" :stroke-width="18" :percentage="this.percentage" />
    </div>
  </div>
  <br>
  <div :style="noInfo">
    <h1>无信息，返回重试</h1>
  </div>
  <div :style="existInfo" style="height:auto">
    <el-timeline>
      <el-descriptions title="基本信息" :column="2" border>
        <el-descriptions-item label="户号" label-align="center" align="center">{{ this.information['houseId'] }}
        </el-descriptions-item>
      </el-descriptions>
      <br>
      <el-timeline-item placement="top">
        <el-card v-if="this.information['addApply'] === null">
          <h3>1.并网申请</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible12 = true">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['addApply'] !== null" :style="style1">
          <h3>1.并网申请</h3>
          <p>{{ this.information['addApply'] }}</p>
          <el-button type="default" size="small" @click="this.dialogVisible12 = true">编辑</el-button>
        </el-card>
      </el-timeline-item>
      <el-dialog v-model="dialogVisible12" title="并网申请" width="40%" style="text-align: center">
        <div style="height: 700px;width: 100%">
          <div style="width: 50%;height: 100%; float: left">
            <h6 v-if="this.process1Have.includes(1)">1.《分布式光伏发电项目申请书》</h6>
            <h6 v-if="!this.process1Have.includes(1)" style="color:red">1.《分布式光伏发电项目申请书》</h6>
            <el-checkbox v-if="this.process1Have.includes(1)" v-model="this.checked11" style="margin-right: 10px"/>
            <el-link v-if="this.process1Have.includes(1)"
              :href="'files/' + this.information.houseId + '/1并网申请/'+fileName11" type="primary">{{this.fileName11}}
            </el-link>
            <h6 v-if="this.process1Have.includes(2)">2.《居民家庭分布式光伏发电项目并网申请表》</h6>
            <h6 v-if="!this.process1Have.includes(2)" style="color:red">2.《居民家庭分布式光伏发电项目并网申请表》</h6>
            <el-checkbox v-if="this.process1Have.includes(2)" v-model="this.checked12" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(2)"
              :href="'files/' + this.information.houseId + '/1并网申请/'+fileName12" type="primary">
              {{this.fileName12}}</el-link>
            <h6 v-if="this.process1Have.includes(3)">3.《村委（街道办）项目同意书》</h6>
            <h6 v-if="!this.process1Have.includes(3)" style="color:red">3.《村委（街道办）项目同意书》</h6>
            <el-checkbox v-if="this.process1Have.includes(3)" v-model="this.checked13" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(3)"
              :href="'files/' + this.information.houseId + '/1并网申请/'+fileName13" type="primary">{{this.fileName13}}
            </el-link>
            <h6 v-if="this.process1Have.includes(4)">4.房产证或者宅基地证的原件及复印件（出证单位盖章）</h6>
            <h6 v-if="!this.process1Have.includes(4)" style="color:red">4.房产证或者宅基地证的原件及复印件（出证单位盖章）</h6>
            <el-checkbox v-if="this.process1Have.includes(4)" v-model="this.checked14" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(4)"
              :href="'files/' + this.information.houseId + '/1并网申请/'+fileName14" type="primary">{{this.fileName14}}
            </el-link>
            <h6 v-if="this.process1Have.includes(5)">5.《项目投产真实性承诺书》</h6>
            <h6 v-if="!this.process1Have.includes(5)" style="color:red">5.《项目投产真实性承诺书》</h6>
            <el-checkbox v-if="this.process1Have.includes(5)" v-model="this.checked15" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(5)"
                     :href="'files/' + this.information.houseId + '/1并网申请/'+fileName15" type="primary">{{this.fileName15}}
            </el-link>
            <h6 v-if="this.process1Have.includes(6)">6.客户居民身份证原件及复印件</h6>
            <h6 v-if="!this.process1Have.includes(6)" style="color:red">6.客户居民身份证原件及复印件</h6>
            <el-checkbox v-if="this.process1Have.includes(6)" v-model="this.checked16" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(6)"
              :href="'files/' + this.information.houseId + '/1并网申请/'+fileName16" type="primary">{{this.fileName16}}</el-link>
            <h6 v-if="this.process1Have.includes(7)">7.本人银行卡原件及复印件</h6>
            <h6 v-if="!this.process1Have.includes(7)" style="color:red">7.本人银行卡原件及复印件</h6>
            <el-checkbox v-if="this.process1Have.includes(7)" v-model="this.checked17" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(7)"
              :href="'files/' + this.information.houseId + '/1并网申请/'+fileName17" type="primary">{{this.fileName17}}
            </el-link>
            <h6 v-if="this.process1Have.includes(8)">8.户口本原价及复印件</h6>
            <h6 v-if="!this.process1Have.includes(8)" style="color:red">8.户口本原价及复印件</h6>
            <el-checkbox v-if="this.process1Have.includes(8)" v-model="this.checked18" style="margin-right: 10px" />
            <el-link v-if="this.process1Have.includes(8)"
                     :href="'files/' + this.information.houseId + '/1并网申请/'+this.fileName18" type="primary">{{this.fileName18}}</el-link>
          </div>
          <div style="width: 50%;height: 100%; float: left" v-if="this.process1Number !== '0'">
            <el-form :model="form" ref="form1">
              <el-form-item prop="process1">
                <el-button @click="this.dialogVisible12 = false" style="margin-left: 10px">取消</el-button>
                <el-button type="primary" @click="upload1(1)">确定</el-button>
                <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile1"
                  style="height: 150px;width: 300px; margin-left: 0; margin-top: 10px;">
                  <div class="el-upload__text">
                    拖动文件或<em>点击上传</em>
                  </div>
                </el-upload>
                
              </el-form-item>
            </el-form>
          </div>
        </div>
        <template #footer>
          <el-popconfirm title="确定删除吗?" @confirm="deleteFile(1)">
            <template #reference>
              <el-button type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(1)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['addSurvey'] !== null" :style="style1">
          <h3>2.接入方案设计及确认</h3>
          <p>{{ this.information['addSurvey'] }}</p>
          <el-button type="default" size="small" @click="showFile2">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['addSurvey'] === null">
          <h3>2.接入方案设计及确认</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible22 = true"
            v-if="this.information['addApply'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>

      <el-dialog v-model="dialogVisible22" title="接入方案设计及确认" width="30%" style="text-align: center">
        <h6 v-if="this.information['process2Name'] === null || this.information['process2Name'] === ''" style="color:red">1. 《关于××项目接入电网意见的函》</h6>
        <h6 v-if="this.information['process2Name'] !== null && this.information['process2Name'] !== ''">1. 《关于××项目接入电网意见的函》</h6>
        <el-link v-if="this.information['process2Name'] !== null && this.information['process2Name'] !== ''" :href="'files/' + this.information.houseId + '/2接入方案设计及确认/'+this.fileName2" type="primary" style="margin-bottom: 10px">
          {{ this.fileName2 }}</el-link>
        <el-form v-if="this.information['process2Name'] === null || this.information['process2Name'] === ''" :model="form" ref="form1">
          <el-form-item prop="process2">
            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile2"
                       style="height: 150px;width: 300px">
              <div class="el-upload__text">
                拖动文件或<em>点击上传</em>
              </div>
            </el-upload>
            <el-button @click="this.dialogVisible22 = false" style="margin-left: 10px">取消</el-button>
            <el-button type="primary" @click="upload1(2)">确定</el-button>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-popconfirm title="确定删除吗?" @confirm="deleteFile(2)">
            <template #reference>
              <el-button v-if="this.information['process2Name'] !== null && this.information['process2Name'] !== ''" type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(2)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['addConfirm'] !== null" :style="style1">
          <h3>3.项目备案</h3>
          <p>{{ this.information['addConfirm'] }}</p>
          <el-button type="default" size="small" @click="showFile3">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['addConfirm'] === null">
          <h3>3.项目备案</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible32 = true"
            v-if="this.information['addSurvey'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>

      <el-dialog v-model="dialogVisible32" title="项目备案" width="30%" style="text-align: center">
        <h6 v-if="this.information['process3Name'] === null || this.information['process3Name'] === ''" style="color:red">1. 《晋中市个人建设分布式光伏发电项目备案表》</h6>
        <h6 v-if="this.information['process3Name'] !== null && this.information['process3Name'] !== ''">1. 《晋中市个人建设分布式光伏发电项目备案表》</h6>
        <el-link v-if="this.information['process3Name'] !== null && this.information['process3Name'] !== ''" :href="'files/' + this.information.houseId + '/3项目备案/'+this.fileName3" type="primary" style="margin-bottom: 10px">
          {{ this.fileName3 }}</el-link>
        <el-form v-if="this.information['process3Name'] === null || this.information['process3Name'] === ''" :model="form" ref="form1">
          <el-form-item prop="process3">
            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile3"
                       style="height: 150px;width: 300px">
              <div class="el-upload__text">
                拖动文件或<em>点击上传</em>
              </div>
            </el-upload>
            <el-button @click="this.dialogVisible32 = false" style="margin-left: 10px">取消</el-button>
            <el-button type="primary" @click="upload1(3)">确定</el-button>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-popconfirm v-if="this.information['process3Name'] !== null && this.information['process3Name'] !== ''" title="确定删除吗?" @confirm="deleteFile(3)">
            <template #reference>
              <el-button type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(3)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['addRecord'] !== null" :style="style1">
          <h3>4.并网验收与调试申请</h3>
          <p>{{ this.information['addRecord'] }}</p>
          <el-button type="default" size="small" @click="showFile4">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['addRecord'] === null">
          <h3>4.并网验收与调试申请</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible42 = true"
            v-if="this.information['addConfirm'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>
      <el-dialog v-model="dialogVisible42" title="并网验收与调试申请" width="30%" style="text-align: center">
        <h6 v-if="this.information['process4Name'] === null || this.information['process4Name'] === ''" style="color:red">1. 《分布式电源并网验收与并网调试申请表》</h6>
        <h6 v-if="this.information['process4Name'] !== null && this.information['process4Name'] !== ''">1. 《分布式电源并网验收与并网调试申请表》</h6>
        <el-link v-if="this.information['process4Name'] !== null && this.information['process4Name'] !== ''" :href="'files/' + this.information.houseId + '/4并网验收与调试申请/'+this.fileName4" type="primary" style="margin-bottom: 10px">
          {{ this.fileName4 }}</el-link>
        <el-form v-if="this.information['process4Name'] === null || this.information['process4Name'] === ''" :model="form" ref="form1">
          <el-form-item prop="process4">
            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile4"
                       style="height: 150px;width: 300px">
              <div class="el-upload__text">
                <em>点击上传</em>
              </div>
            </el-upload>
            <el-button @click="this.dialogVisible42 = false" style="margin-left: 10px">取消</el-button>
            <el-button type="primary" @click="upload1(4)">确定</el-button>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-popconfirm title="确定删除吗?" @confirm="deleteFile(4)">
            <template #reference>
              <el-button v-if="this.information['process4Name'] !== null && this.information['process4Name'] !== ''" type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(4)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['connectApply'] !== null" :style="style1">
          <h3>5.安装计量装置</h3>
          <p>{{ this.information['connectApply'] }}</p>
          <el-button type="default" size="small" @click="showFile5">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['connectApply'] === null">
          <h3>5.安装计量装置</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible52 = true"
            v-if="this.information['addRecord'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>
      <el-dialog v-model="dialogVisible52" title="并网验收与调试申请" width="30%" style="text-align: center">
        <h6 v-if="this.information['process5Name'] === null || this.information['process5Name'] === ''" style="color:red">1. 《用电工作票》</h6>
        <h6 v-if="this.information['process5Name'] !== null && this.information['process5Name'] !== ''">1. 《用电工作票》</h6>
        <el-link v-if="this.information['process5Name'] !== null && this.information['process5Name'] !== ''" :href="'files/' + this.information.houseId + '/5安装计量装置/'+this.fileName5" type="primary" style="margin-bottom: 10px">
          {{ this.fileName5 }}</el-link>
        <el-form v-if="this.information['process5Name'] === null || this.information['process5Name'] === ''" :model="form" ref="form1">
          <el-form-item prop="process5">
            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile5"
                       style="height: 150px;width: 300px">
              <div class="el-upload__text">
                <em>点击上传</em>
              </div>
            </el-upload>
            <el-button @click="this.dialogVisible52 = false" style="margin-left: 10px">取消</el-button>
            <el-button type="primary" @click="upload1(5)">确定</el-button>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-popconfirm title="确定删除吗?" @confirm="deleteFile(5)">
            <template #reference>
              <el-button v-if="this.information['process5Name'] !== null && this.information['process5Name'] !== ''" type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(5)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['connectDevice'] !== null" :style="style1">
          <h3>6.签署合同及协议</h3>
          <p>{{ this.information['connectDevice'] }}</p>
          <el-button type="default" size="small" @click="showFile6">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['connectDevice'] === null">
          <h3>6.签署合同及协议</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible62 = true"
            v-if="this.information['connectApply'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>
      <el-dialog v-model="dialogVisible62" title="签署合同及协议" width="30%" style="text-align: center">
        <h6 v-if="this.information['process6Name'] === null || this.information['process6Name'] === ''" style="color:red">1.	《分布式光伏发电项目低压发用电合同》</h6>
        <h6 v-if="this.information['process6Name'] !== null && this.information['process6Name'] !== ''">1.	《分布式光伏发电项目低压发用电合同》</h6>
        <el-link v-if="this.information['process6Name'] !== null && this.information['process6Name'] !== ''" :href="'files/' + this.information.houseId + '/6签署合同及协议/'+this.fileName6" type="primary" style="margin-bottom: 10px">
          {{ this.fileName6 }}</el-link>
        <el-form v-if="this.information['process6Name'] === null || this.information['process6Name'] === ''" :model="form" ref="form1">
          <el-form-item prop="process6">
            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile6"
                       style="height: 150px;width: 300px">
              <div class="el-upload__text">
                <em>点击上传</em>
              </div>
            </el-upload>
            <el-button @click="this.dialogVisible62 = false" style="margin-left: 10px">取消</el-button>
            <el-button type="primary" @click="upload1(6)">确定</el-button>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-popconfirm title="确定删除吗?" @confirm="deleteFile(6)">
            <template #reference>
              <el-button v-if="this.information['process6Name'] !== null && this.information['process6Name'] !== ''" type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(6)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['connectSign'] !== null" :style="style1">
          <h3>7.并网验收与调试</h3>
          <p>{{ this.information['connectSign'] }}</p>
          <el-button type="default" size="small" @click="showFile7">编辑</el-button>
        </el-card>
        <el-card v-if="this.information['connectSign'] === null">
          <h3>7.并网验收与调试</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible72 = true"
            v-if="this.information['connectDevice'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>
<!--      <el-dialog v-model="dialogVisible7" title="并网验收与调试" width="50%">-->
<!--        <h6>1.《分布式光伏发电项目并网验收意见单》</h6>-->
<!--        <h6>2.《分布式电源并网验收意见会签单》</h6>-->
<!--        <el-form :model="form" ref="form1" label-width="400px">-->
<!--          <el-form-item prop="process7">-->
<!--            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile7"-->
<!--              style="height: 150px;width: 300px">-->
<!--              <div class="el-upload__text">-->
<!--                <em>点击上传</em>-->
<!--              </div>-->
<!--            </el-upload>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--        <template #footer>-->
<!--          <span class="dialog-footer">-->
<!--            <el-button @click="this.dialogVisible7 = false">取消</el-button>-->
<!--            <el-button type="primary" @click="upload1(7)">确定</el-button>-->
<!--          </span>-->
<!--        </template>-->
<!--      </el-dialog>-->
      <el-dialog v-model="dialogVisible72" title="并网验收与调试" width="30%" style="text-align: center">
        <h6 v-if="this.process7Have.includes(1)">1. 《分布式光伏发电项目并网验收意见单》</h6>
        <h6 v-if="!this.process7Have.includes(1)" style="color:red">1. 《分布式光伏发电项目并网验收意见单》</h6>
        <el-checkbox v-if="this.process7Have.includes(1)" v-model="this.checked71" style="margin-right: 10px"/>
        <el-link v-if="this.process7Have.includes(1)"
          :href="'files/' + this.information.houseId + '/7并网验收与调试/'+this.fileName71" type="primary">
          {{this.fileName71}}</el-link>
        <h6 v-if="this.process7Have.includes(2)">2. 《分布式电源并网验收意见会签单》</h6>
        <h6 v-if="!this.process7Have.includes(2)" style="color:red">2. 《分布式电源并网验收意见会签单》</h6>
        <el-checkbox v-if="this.process7Have.includes(2)" v-model="this.checked72" style="margin-right: 10px"/>
        <el-link v-if="this.process7Have.includes(2)"
          :href="'files/' + this.information.houseId + '/7并网验收与调试/'+this.fileName72" type="primary">
          {{this.fileName72}}
        </el-link>
        <div style="height:30px;width: 100%"></div>
        <el-form :model="form" ref="form1">
          <el-form-item prop="process7">
            <el-upload class="upload-demo" drag action="#" multiple :http-request="getFile7"
              style="height: 150px;width: 300px" v-if="this.process7Number !== '2'">
              <div class="el-upload__text">
                <em>点击上传</em>
              </div>
            </el-upload>
            <el-button @click="this.dialogVisible72 = false" style="margin-left: 10px">取消</el-button>
            <el-button type="primary" @click="upload1(7)">确定</el-button>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-popconfirm title="确定删除吗?" @confirm="deleteFile(7)">
            <template #reference>
              <el-button type="danger" style="margin-left: 10px; float: left">删除</el-button>
            </template>
          </el-popconfirm>
          <span class="dialog-footer">
            <el-button type="primary" @click="finish(7)">完成</el-button>
          </span>
        </template>
      </el-dialog>

      <el-timeline-item placement="top">
        <el-card v-if="this.information['connectCheck'] !== null" :style="style1">
          <h3>8.并网运行</h3>
          <p>{{ this.information['connectCheck'] }}</p>
        </el-card>
        <el-card v-if="this.information['connectCheck'] === null">
          <h3>8.并网运行</h3>
          <el-button type="primary" size="small" @click="this.dialogVisible8 = true"
            v-if="this.information['connectSign'] !== null">编辑</el-button>
        </el-card>
      </el-timeline-item>
      <el-dialog v-model="dialogVisible8" title="并网运行" width="20%">
        <el-button type="primary" @click="upload1(8)" style="width: 50%;height: 50%; margin-left: 20%">已验收，允许并网
        </el-button>
        <el-button @click="this.dialogVisible8 = false">取消</el-button>
      </el-dialog>
    </el-timeline>
    <div>预计完成时间：{{ finishTime }}</div>
  </div>

</template>

<script >
import { ElMessage } from "element-plus";
import request from "@/utils/request";

export default {
  methods: {
    load() {
      // if (window.sessionStorage.getItem('message')){
      //   this.message= window.sessionStorage.getItem('message')
      //   ElMessage({
      //     message: this.message,
      //     type: "success"
      //   })
      //   window.sessionStorage.removeItem('message')
      // }
      request.get("/grid-connect/getOne", {
        params: {
          id: this.id
        }
      }).then(res => {
        this.information = res.data
        //计算完成百分比
        if (this.information['isComplete'] === 1) {
          this.percentage = 100
        } else if (this.information['connectSign'] !== null) {
          this.percentage = 87.5
        } else if (this.information['connectDevice'] !== null) {
          this.percentage = 75
        } else if (this.information['connectApply'] !== null) {
          this.percentage = 62.5
        } else if (this.information['addRecord'] !== null) {
          this.percentage = 50
        } else if (this.information['addConfirm'] !== null) {
          this.percentage = 37.5
        } else if (this.information['addSurvey'] !== null) {
          this.percentage = 25
        } else if (this.information['addApply'] !== null) {
          this.percentage = 12.5
        }
        this.process1Have = this.information['process1Number']
        this.process7Have = this.information['process7Number']
        let name = this.information['process1Name'].split(',')
        for (var i = 0; i < name.length; i++){
          if(name[i].includes("申请书")){
            this.fileName11 = name[i]
          }else if(name[i].includes("申请表")){
            this.fileName12 = name[i]
          }else if(name[i].includes("同意书")){
            this.fileName13 = name[i]
          }else if(name[i].includes("房产证")){
            this.fileName14 = name[i]
          }else if(name[i].includes("承诺书")){
            this.fileName15 = name[i]
          }else if(name[i].includes("身份证")){
            this.fileName16 = name[i]
          }else if(name[i].includes("银行卡")){
            this.fileName17 = name[i]
          }else if(name[i].includes("户口本")){
            this.fileName18 = name[i]
          }
        }
        let name7 = this.information['process7Name'].split(',')
        for (i = 0; i < name7.length; i++){
          if(name7[i].includes("意见单")){
            this.fileName71 = name7[i]
          }else if(name7[i].includes("会签单")){
            this.fileName72 = name7[i]
          }
        }
        this.fileName2 = this.information['process2Name']
        this.fileName3 = this.information['process3Name']
        this.fileName4 = this.information['process4Name']
        this.fileName5 = this.information['process5Name']
        this.fileName6 = this.information['process6Name']
      })
      request.get("/grid-connect/getFinishTime", {
        params: {
          id: this.id
        }
      }).then(res => {
        this.finishTime = res.data
      })
    },
    showFile1() {
      this.dialogVisible12 = true
    },
    showFile2() {
      this.dialogVisible22 = true
    },
    showFile3() {
      this.dialogVisible32 = true
    },
    showFile4() {
      this.dialogVisible42 = true
    },
    showFile5() {
      this.dialogVisible52 = true
    },
    showFile6() {
      this.dialogVisible62 = true
    },
    showFile7() {
      this.dialogVisible72 = true
    },
    showFile8() {
      this.dialogVisible82 = true
    },
    getFile1(item) {
      const filename = item.file.name.split('.')[0]
      if (filename.includes("申请书")) {
        this.file11 = item.file
      } else if (filename.includes("申请表")) {
        this.file12 = item.file
      } else if (filename.includes("同意书")) {
        this.file13 = item.file
      } else if (filename.includes("房产证")) {
        this.file14 = item.file
      } else if (filename.includes("承诺书")) {
        this.file15 = item.file
      } else if (filename.includes("身份证")) {
        this.file16 = item.file
      } else if (filename.includes("银行卡")) {
        this.file17 = item.file
      } else if (filename.includes("户口本")) {
        this.file18 = item.file
      }else{
        ElMessage({
              message: "无效文件，请检查文件名！",
              type: "error"
            })
      }
    },
    getFile2(item) {
      this.file2 = item.file
    },
    getFile3(item) {
      this.file3 = item.file
    },
    getFile4(item) {
      this.file4 = item.file
    },
    getFile5(item) {
      this.file5 = item.file
    },
    getFile6(item) {
      this.file6 = item.file
    },
    getFile7(item) {
      const filename = item.file.name.split('.')[0]
      if (filename.includes("意见单")) {
        this.file71 = item.file
      } else if (filename.includes("会签单")) {
        this.file72 = item.file
      }else{
        ElMessage({
              message: "无效文件，请检查文件名！",
              type: "error"
            })
      }
    },
    finish(process){
      const fd = new FormData()
      fd.append("id", this.id)
      fd.append("process", process)
      request.post("/grid-connect/completeBusiness", fd).then(res => {
        if (res) {
          ElMessage({
            message: "完成成功",
            type: "success"
          })
        } else {
          ElMessage({
            message: "完成失败",
            type: "error"
          })
        }
      })
      location.reload()
    },
    deleteFile(process){
      if(process === 1){
        let list = ""
        if (this.checked11 === true){
          list += "1"
        }
        if (this.checked12 === true){
          list += "2"
        }
        if (this.checked13 === true){
          list += "3"
        }
        if (this.checked14 === true){
          list += "4"
        }
        if (this.checked15 === true){
          list += "5"
        }
        if (this.checked16 === true){
          list += "6"
        }
        if (this.checked17 === true){
          list += "7"
        }
        if (this.checked18 === true){
          list += "8"
        }
        request.delete("/grid-connect/deleteFile1", {
          params: {
            fileList: list,
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }else if(process === 2){
        request.delete("/grid-connect/deleteFile2", {
          params: {
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }else if(process === 3){
        request.delete("/grid-connect/deleteFile3", {
          params: {
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }else if(process === 4){
        request.delete("/grid-connect/deleteFile4", {
          params: {
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }else if(process === 5){
        request.delete("/grid-connect/deleteFile5", {
          params: {
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }else if(process === 6){
        request.delete("/grid-connect/deleteFile6", {
          params: {
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }else if(process === 7){
        let list = ""
        if (this.checked71 === true){
          list += "1"
        }
        if (this.checked72 === true){
          list += "2"
        }
        request.delete("/grid-connect/deleteFile7", {
          params: {
            fileList: list,
            id: this.id,
            houseId: this.information["houseId"]
          }
        }).then(res => {
          if (res === 1) {
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
      }

      location.reload()
    },
    upload1(process) {
      const fd = new FormData()
      if (process === 1) {
        fd.append('file11', this.file11)
        fd.append('file12', this.file12)
        fd.append('file13', this.file13)
        fd.append('file14', this.file14)
        fd.append('file15', this.file15)
        fd.append('file16', this.file16)
        fd.append('file17', this.file17)
        fd.append('file18', this.file18)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        console.log(this.file18)
        request.post('/grid-connect/uploading1', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          // window.sessionStorage.setItem("message", response)
          this.dialogVisible12 = false
          location.reload()
        })
      } else if (process === 2) {
        fd.append('file2', this.file2)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        request.post('/grid-connect/uploading2', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible22 = false
          location.reload()
        })
      } else if (process === 3) {
        fd.append('file3', this.file3)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        request.post('/grid-connect/uploading3', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible32 = false
          location.reload()
        })
      } else if (process === 4) {
        fd.append('file4', this.file4)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        request.post('/grid-connect/uploading4', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible42 = false
          location.reload()
        })
      } else if (process === 5) {
        fd.append('file5', this.file5)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        request.post('/grid-connect/uploading5', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible52 = false
          location.reload()
        })
      } else if (process === 6) {
        fd.append('file6', this.file6)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        request.post('/grid-connect/uploading6', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible62 = false
          location.reload()
        })
      }
      else if (process === 7) {
        fd.append('file71', this.file71)
        fd.append('file72', this.file72)
        const config = {
          headers: { 'Content-Type': 'multipart/form-data' },
          params: {
            houseId: this.information["houseId"],
            id: this.id
          }
        }
        request.post('/grid-connect/uploading7', fd, config
        ).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible72 = false
          location.reload()
        })
      } else if (process === 8) {
        request.post('/grid-connect/uploading8/' + this.id).then(response => {
          ElMessage({
            message: response,
            type: "success"
          })
          this.dialogVisible82 = false
          location.reload()
        })
      }
      // 刷新页面
      // location.reload()
      // this.$router.push({
      //   name: "blank",
      //   query: {
      //     id: this.id
      //   }
      // })

    },

  },
  data() {
    return {
      id: null,
      message: "",
      finishTime: "",
      path: "",
      percentage: 0,
      process1Number: null,
      process7Number: null,
      process1Have: null,
      process7Have: null,
      publicPath: process.env.BASE_URL,
      dialogVisible1: false,
      dialogVisible12: false,
      dialogVisible2: false,
      dialogVisible22: false,
      dialogVisible3: false,
      dialogVisible32: false,
      dialogVisible4: false,
      dialogVisible5: false,
      dialogVisible6: false,
      dialogVisible7: false,
      dialogVisible8: false,
      dialogVisible42: false,
      dialogVisible52: false,
      dialogVisible62: false,
      dialogVisible72: false,
      dialogVisible82: false,
      information: {},
      file11: {},
      file12: {},
      file13: {},
      file14: {},
      file15: {},
      file16: {},
      file17: {},
      file18: {},
      fileName11: "",
      fileName12: "",
      fileName13: "",
      fileName14: "",
      fileName15: "",
      fileName16: "",
      fileName17: "",
      fileName18: "",
      fileName71: "",
      fileName72: "",
      fileName2: "",
      fileName3: "",
      fileName4: "",
      fileName5: "",
      fileName6: "",
      checked11: false,
      checked12: false,
      checked13: false,
      checked14: false,
      checked15: false,
      checked16: false,
      checked17: false,
      checked18: false,
      checked71: false,
      checked72: false,
      file2: {},
      file3: {},
      file4: {},
      file5: {},
      file6: {},
      file71: {},
      file72: {},
      form: {},
      fileList: [],
      processes: ['addApply', 'addSurvey', 'addConfirm', 'addRecord', 'connectApply', 'connectDevice', 'connectSign', 'connectCheck',],
      noInfo: {
        width: "40%",
        height: "200px",
        display: "none",
      },
      existInfo: {
        width: "40%",
        height: "1000px",
        display: "block"
      },
      style1: {
        background: "#0bbd87",
        color: "white",
      },
    }
  },
  created() {
    if (typeof this.$route.query.id !== "undefined") {
      this.noInfo.display = "none"
      this.existInfo.display = "block"
      this.id = this.$route.query.id
    } else {
      this.noInfo.display = "block"
      this.existInfo.display = "none"
    }
    this.load()
  },
}

</script>
<style scoped>
.el-dialog_body {
  height: 70vh;
  overflow: auto;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>