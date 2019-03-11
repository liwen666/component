'use strict';
//node.js的单元测试
let expect = require('chai').expect;

let grpcc = require('../lib');
console.log("测试脚本")

describe('grpcc', () => {



  it(' should fail if bad exec script is loaded', () => {
    let args = {
      proto: './test/grpc.proto',
      address: ':50000',
      exec: './test/badexec/badexec.js',
    };

    let fn = grpcc.bind(null, args, {});
    expect(fn).to.throw(/unexpected identifier/i);
  });
});
// var call = client.sayClientStream({}, pr);
// let i = 0;
// let id = setInterval(() => {
//     if (i > 5) {
//         clearInterval(id);
//         return call.end();
//     }
//     i++;
//     call.write({ sayWhat: `hello ${i}` });
// }, 100);
it('should fail if bad exec script is loaded', () => {
  console.log("000","jjjj",'*');
});
console.log("000","jjjj",'*');