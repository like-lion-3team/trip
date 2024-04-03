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
        if (!response.ok){
            alert("토큰 재발급 요청 실패")
            window.location.href = "/login"
        }

        let data = await response.json();
        console.log(data, "데이터")
        localStorage.setItem('token', data.accessToken);

        response = await fetch(url, {
            headers: { 'Authorization': `Bearer ${data.accessToken}` }
        });
        if (!response.ok){
            alert("토큰 재발급 요청 실패")
            window.location.href = "/login"
        }


        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
}

window.refreshTokenIfNeeded = retryTokenRequest;
