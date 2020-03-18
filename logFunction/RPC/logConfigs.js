const secrets = require("../secrets");
const PROTO_PATH = "./configs.proto";
const grpc = require("grpc");
const protoLoader = require('@grpc/proto-loader');
const packageDefinition = protoLoader.loadSync(PROTO_PATH, { 
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
});

function configsRPC(configs) {
    console.log(packageDefinition);
    var routeguide = grpc.loadPackageDefinition(packageDefinition).routeguide;
    var creds = grpc.credentials.createInsecure();
    var client = new routeguide.RouteGuide('localhost:7789', creds);

    return new Promise((resolve, reject) => {
        let call = client.GetConfigs(configs, secrets.rpcSecret);

        call.on("data", data => { resolve(data); });

        call.on("error", err => { reject(err); });
    });
}

module.exports = configsRPC;