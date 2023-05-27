import axios from 'axios'
//此处忽略了部分非必要的导入

// create an axios instance
const service = axios.create({
//baseUrl请修改为你自己的url
  baseURL: '/api', // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})
// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
      let disposition = response.headers['content-disposition'];
      //以下部分有需要优化，如后端返回时没有携带文件后缀名,没有.时会有问题等等
      let filename = disposition?disposition.substring(disposition.indexOf('=')+1,disposition.indexOf('.')):"下载文件";
      let newName = decodeURI(escape(filename))
      let extName =disposition.substring(disposition.indexOf('.')+1)
      let blob = new Blob([response.data],{type: 'application/vnd.ms-excel'});
      let link = document.createElement("a");
      let evt = document.createEvent("HTMLEvents");
      evt.initEvent("click", false, false);
      link.href = URL.createObjectURL(blob);
      link.download = newName+"."+extName;
      link.style.display = "none";
      document.body.appendChild(link);
      link.click();
      window.URL.revokeObjectURL(link.href);
  },
  error => {
    console.log('err' + error) // for debug
    return Promise.reject(error)
  }
)

export default service