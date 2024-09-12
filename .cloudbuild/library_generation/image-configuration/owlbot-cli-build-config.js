/**
 * @fileoverview This file contains the esbuild configuration to compile the
 * owlbot-cli source code into a single bundled javascript file
 * @author diegomarquezp
 */
const { build } = require("esbuild");


const sharedConfig = {
  entryPoints: ["src/bin/owl-bot.ts"],
  bundle: true,
  minify: false,
};

build({
  ...sharedConfig,
  platform: 'node',
  format: 'cjs',
  outfile: "build/bundle.js",
});
