<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<#assign numberOfPCs = 0>

    <h1 style="text-align: center; padding: 30px 0px">
        List OF PCs!
    </h1>

    <div>
        <div>
            <table>
                <thead >
                    <tr>
                        <td>Number</td>
                        <td>OS Name</td>
                        <td>OS Architecture</td>
                        <td>Hostname</td>
                        <td>IP Address</td>
                        <td>MAC Address</td>
                        <td>User Name</td>
                        <td>Is Online</td>
                    </tr>
                </thead>
                <tbody>

                <#list pcModels as pc>
                    <tr>
                        <td><#assign numberOfPCs = numberOfPCs+1/> ${numberOfPCs}</td>
                        <td>${pc.os_name}</td>
                        <td>${pc.os_arch}</td>
                        <td>${pc.hosname}</td>
                        <td>${pc.ip_address}</td>
                        <td>#{pc.mac_address}</td>
                        <td>${pc.user_name}</td>
                        <td><#if pc.is_Online==true??>
                                <div style="margin: 0 auto; height: 10px; width: 10px; border-radius: 100px; background-color: forestgreen"></div>
                                <#else>
                                    <div style="margin: 0 auto; height: 10px; width: 10px; border-radius: 100px; background-color: darkred"></div>

                            </#if>
                            </td>
                    </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>

</body>
</html>