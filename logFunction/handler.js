'use strict';
const decryption = require("./decryption");
const logConfigs = require("./RPC/logConfigs");

module.exports.logSQSMsgs = async event => {

  logConfigs(["config1", "config3", "config4"]).then(data => {
    console.log(data);
  }).catch (err => {
    console.log(err);
  });
  
  for (record of event.records) {
    let id = record.body.split(" ")[0];
    let encodedMsg = record.body.split(" ")[1];
    let log = {"id": id, "message": decryption(encodedMsg)}
    console.log(log);
  }

  return { statusCode: 200 };

};
