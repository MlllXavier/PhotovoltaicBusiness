<template>
  <div class="login">
    <vue-particles class="main-bg" color="#409EFF" :particleOpacity="0.4" :particlesNumber="60" shapeType="circle"
      :particleSize="6" linesColor="#409EFF" :linesWidth="1" :lineLinked="true" :lineOpacity="0.2" :linesDistance="150"
      :moveSpeed="3" :hoverEffect="true" hoverMode="grab" :clickEffect="true" clickMode="push">
    </vue-particles>
    <div class="loginview">
      <div class="input">
        <h1>光伏业务管理</h1>
        <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="60px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="user">
          <el-input v-model="ruleForm.user" />
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input v-model="ruleForm.pass" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
          <el-button type="success" @click="showDialog">注册</el-button>
        </el-form-item>
      </el-form>
      </div>
      <el-dialog v-model="dialogVisible" title="用户注册" width="50%">
        <el-form label-width="120px" :rules="rules1">
          <el-form-item label="1.用户名">
            <el-input v-model="username" style="width: 80%" />
          </el-form-item>
          <el-form-item label="2.密码">
            <el-input v-model="password" style="width: 80%" />
          </el-form-item>
          <el-form-item label="3.再次输入密码">
            <el-input v-model="password2" style="width: 80%" />
          </el-form-item>
          <el-form-item label="4.验证口令">
            <el-input v-model="key" style="width: 80%" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="register">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: false,
      username: '',
      password: '',
      password2: '',
      key: '',
      user0: {},
      rules1: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        password2: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
        ],
        key: [
          { required: true, message: '请输入验证口令', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    showDialog() {
      this.dialogVisible = true;
    },
    register() {
      if (this.username !== '') {
        if (this.password !== '') {
          if (this.password2 !== '') {
            if (this.key !== '') {
              if (this.password === this.password2) {
                if (this.key === 'admin') {
                  this.user0.username = this.username;
                  this.user0.password = this.password;
                  request.post("/login", this.user0).then(res => {
                    console.log(res)
                    if (res.code === 1000) {
                      ElMessage({
                        message: "注册成功",
                        type: "success"
                      })
                      this.dialogVisible = false;
                    } else {
                      ElMessage({
                        message: "用户已存在",
                        type: "error"
                      })
                    }
                  })
                } else {
                  ElMessage({
                    message: "口令错误",
                    type: "error"
                  })
                }

              } else {
                ElMessage({
                  message: "密码不一致",
                  type: "error"
                })
              }
            } else {
              ElMessage({
                message: "请输入验证口令",
                type: "error"
              })
            }
          } else {
            ElMessage({
              message: "请再次输入密码",
              type: "error"
            })
          }
        } else {
          ElMessage({
            message: "请输入密码",
            type: "error"
          })
        }
      } else {
        ElMessage({
          message: "请输入用户名",
          type: "error"
        })
      }
    }
  }
}
</script>

<script setup>
import router from '@/router'
import store from "@/store";
import { reactive, ref } from 'vue'
import request from "@/utils/request";
import { ElMessage } from "element-plus";

const ruleForm = reactive({
  user: '',
  pass: '',
})
const ruleFormRef = ref({})

const checkUser = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  }
  else {
    callback()
  }
}

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  }
  else {
    callback()
  }
}
const rules = reactive({
  user: [{ validator: checkUser, trigger: 'blur' }],
  pass: [{ validator: validatePass, trigger: 'blur' }],
})

const login = () => {
  const token = '123'
  store.commit('setToken', token)
  router.push({ name: 'home' })
}

const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
      request.get("/login/getPassword", {
        params: {
          username: ruleForm.user
        }
      }).then(res => {
        if (res.data === "FAILED") {
          ElMessage({
            message: "用户名错误",
            type: "error"
          })
        } else {
          if (res.data == ruleForm.pass) {
            ElMessage({
              message: "登录成功",
              type: "success"
            })
            login()
          } else {
            ElMessage({
              message: "密码错误",
              type: "error"
            })
          }
        }
      })
    } else {
      console.log('error submit!')
      return false
    }
  })
}

</script>

<style>
.login{
  width: 100%;
  height: 98vh;
  background-image: url(../assets/bg.jpg);
}

.input {
  padding-top: 15%;
  text-align: center;
}

.demo-ruleForm {
  width: 20%;
  margin: 0 auto;
}

#particles-js {
  width: calc(100% - 20px);
  height: calc(100% - 20px);
  position: absolute;
  z-index: 0;
  
}
</style>