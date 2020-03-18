'use strict';
const decryption = require("./decryption");

module.exports.logSQSMsgs = async event => {
  // Get configs
  // Print configs

  for (record of records) {
    let id = record.body.split(" ")[0];
    let encodedMsg = record.body.split(" ")[1];
    let log = {"id": id, "message": decryption(encodedMsg)}
    console.log(log);
  }

  return {
    statusCode: 200,
    body: JSON.stringify(
      {
        message: 'Go Serverless v1.0! Your function executed successfully!',
        input: event,
      },
      null,
      2
    ),
  };

};
