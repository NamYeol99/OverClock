const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
});
const target = "http://localhost:9090";
module.exports = defineConfig({
  configureWebpack: {
    performance: {
      maxEntrypointSize: 5120000,
      maxAssetSize: 5120000,
    },
  },

  devServer: {
    port: 8080,
    proxy: {
      "/overclock/api": { target, changeOrigin: true },
      "/overclock/register": { target, changeOrigin: true },
      "/overclock/member": { target, changeOrigin: true },
    },
  },

  publicPath: "/overclock/",
  transpileDependencies: ["vue-meta"],

  outputDir: "C:/project/Overclock/overclock/src/main/resources/static",
});
