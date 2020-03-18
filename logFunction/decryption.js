const crypto = require('crypto');
const secrets = require('./secrets');

function decrypt (messagebase64) {

    const key = secrets.decryptionKey;
    const iv = secrets.iv;

    const decipher = crypto.createDecipheriv("aes-128-cbc", key, iv);
    let decrypted = decipher.update(messagebase64, 'base64');
    decrypted += decipher.final();
    return decrypted;
}

module.exports = decrypt;