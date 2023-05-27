const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')

module.exports = defineConfig({
  publicPath:"./",
  transpileDependencies: true,
  devServer: {
    open: true,
    host: 'localhost',
    port: 9876,
    https: false,
    proxy: {
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        target: 'http://localhost:8888',     //代理的目标地址
        ws: true,
        changeOrigin: true,              //是否设置同源，输入是的
        pathRewrite: {                   //路径重写
          '^/api': ''                     //选择忽略拦截器里面的内容
        }
      }
    }
  },
  configureWebpack: (config) => {
    // 判断为生产模式下，因为开发模式我们是想保存console的
    if (process.env.NODE_ENV === "production") {
      config.optimization.minimizer.map((arg) => {
        const option = arg.options.terserOptions.compress;
        option.drop_console = true; // 打开开关
        return arg;
      });
    }
  },
  // eslint-disable-next-line no-dupe-keys
  configureWebpack: {
    // 关闭 webpack 的性能提示
    // performance: {
    //   hints:false
    // }

    // //或者

    // 警告 webpack 的性能提示
    performance: {
      hints: 'warning',
      // 入口起点的最大体积
      maxEntrypointSize: 50000000,
      // 生成文件的最大体积
      maxAssetSize: 30000000,
      // 只给出 js 文件的性能提示
      assetFilter: function (assetFilename) {
        return assetFilename.endsWith('.js')
      }
    }
  },
  chainWebpack: (config) => {
    config
      .plugin('AutoImport')
      .use(AutoImport({ resolvers: [ElementPlusResolver()] }))
    config
      .plugin('Components')
      .use(Components({ resolvers: [ElementPlusResolver()] }))
  }
})
