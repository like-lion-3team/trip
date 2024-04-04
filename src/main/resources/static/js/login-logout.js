const showLogin = () => {
    const loginLogoutSpinner = document.getElementById("login-logout-spinner")
    const loginBtn = document.getElementById("loginBtn");
    const logoutBtn = document.getElementById("logoutBtn");

    loginLogoutSpinner.style.display = "none";
    loginBtn.style.display = "block";
    logoutBtn.style.display = "none";
}

const showLogout = () => {
    const loginLogoutSpinner = document.getElementById("login-logout-spinner")
    const loginBtn = document.getElementById("loginBtn");
    const logoutBtn = document.getElementById("logoutBtn");

    loginLogoutSpinner.style.display = "none";
    loginBtn.style.display = "none";
    logoutBtn.style.display = "block";
}

const checkLoginStatus = async () => {
    // 로그인 상태 체크 로직 (예: 쿠키나 세션, 로컬스토리지 등을 사용하여 구현)

    const url = "/api/v1/auth/is-login"
    const token = localStorage.getItem("token");




    if(!token){
        showLogin();
        return;
    }

    const response = await fetch(url, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    console.log(response,"response")
    if(response.status === 200){
        showLogout();
    }else{
        showLogin();
    }
};

window.isLogin = checkLoginStatus;