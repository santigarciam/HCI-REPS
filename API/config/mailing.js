// https://ethereal.email/
module.exports = {
    service: {
        host: 'smtp.ethereal.email',
        port: 587,
        secure: false,
        user: 'bertram.hane15@ethereal.email',
        password: 'Z2aVSu4xeCPqvx3hMb'
    },
    registerVerification: {
        from: 'REPS <noreply@reps.com>',
        subject: 'Codigo para ingresar a REPS',
        htmlBodyTemplate:
            `<div style="background-image: url(principal2.png);">
                <h1 style="text-align: center;">
                    <strong>Bienvenido a <span style="color: #258be5;">REPS</span></strong>
                </h1>
                <img src="./reps4.png" alt="Logo REPS" >
                <p>
                    <span>Codigo valido hasta <%EXPIRATION_DATE%></span>
                </p>
                <div>
                 <h1 style="text-align: center;">
                    <strong>¡Ahora eres parte de nuestro equipo!</strong>
                    <strong>¡Estas a un paso de comenzar a crear tus rutinas!</strong>
                </div>
                <div align="center"> 
                    <a href="<%CONFIRM_LINK%>">  <span style="color: #258be5;">Click para ingresar su codigo</span></a>
                <div>
                <h1 style="text-align: center;">
                    <strong>Your code is <span style="color: #258be5;"><%CODE%></span></strong>
                </h1>
            </div>`,
        confirmationLink: 'http://localhost:8080/#/ConfirmacionMail',
        codeExpirationInterval: 24 * 60 * 60 * 1000
    }
}