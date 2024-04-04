async function retryTokenRequest(url) {
    const token = localStorage.getItem("token")
    const uuid = localStorage.getItem("uuid")

    try {

        // 토큰 재발급 요청
        let response = await fetch('http://localhost:8080/api/v1/auth/token', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ accessToken: token,uuid: uuid})
        });
        let data = await response.json()
        if (!response.ok){
            alert(data.message)
            window.location.href = "/login"
            return
        }

        console.log(data, "데이터")
        localStorage.setItem('token', data.accessToken);

        const retryResponse = await fetch(url, {
            headers: { 'Authorization': `Bearer ${data.accessToken}` }
        });
        if (!response.ok){
            alert(data.message)
            window.location.href = "/login"
            return
        }


        return {response, retryResponse};
    } catch (error) {
        console.error(error);
        throw error;
    }
}

window.refreshTokenIfNeeded = retryTokenRequest;
