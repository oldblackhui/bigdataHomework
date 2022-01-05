const path = require('path')
const resolve = dir => {
  return path.join(__dirname, dir)
}
module.exports = {
  publicPath: './',
  chainWebpack: config => {
    config.resolve.alias
      .set('_c', resolve('src/components')) // key,value自行定义，比如.set('@@', resolve('src/components'))
  },
  //externals属性：防止将某些import的包package打包到bundle中，而是在runtime运行时再去从外部获取这些external dependencies扩展依赖
	/* externals:{
    'BMap': 'BMap',
    'BMap_Symbol_SHAPE_POINT':'BMap_Symbol_SHAPE_POINT'
 } */
}