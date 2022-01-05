// 当通过webpack进行打包时，就会读取该配置文件中的配置信息，通过配置信息来读取文件是如何打包的
// 通过path获取require导入的这个模块
const path = require('path');
// 导出配置信息
module.exports = {
    // 指定新的入口和出口
    entry: "./src/main.js",
    output: {
        // 对路径做一个拼接，__dirname是获取当前文件所在的路径
        path: path.resolve(__dirname, "./dist"),
        filename: "bundle.js" // 设置默认打包后的文件的名字
    }
}