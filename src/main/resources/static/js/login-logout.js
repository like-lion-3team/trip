document.addEventListener('DOMContentLoaded', function() {
    const loginLogoutButton = document.getElementById('loginLogoutButton');

    const checkLoginStatus = () => {
        // 로그인 상태 체크 로직 (예: 쿠키나 세션, 로컬스토리지 등을 사용하여 구현)

        if (loginLogoutButton.innerText === '로그인') {
            loginLogoutButton.innerText = '로그아웃';
        } else {
            loginLogoutButton.innerText = '로그인';
        }
    };

    loginLogoutButton.addEventListener('click', function() {
        if (loginLogoutButton.innerText === '로그인') {
            // 로그인 처리 로직 (예: 로그인 모달 표시, 페이지 이동 등)
            alert('로그인을 진행합니다.');
        } else {
            // 로그아웃 처리 로직 (예: 서버로 로그아웃 요청, 세션 삭제 등)
            fetch('/api/v1/auth/sign-out', {
                method: 'POST',
                credentials: 'include'  // 쿠키를 포함하여 요청
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // 로그아웃 성공 시 처리 (홈 페이지로 리다이렉트)
                        window.location.href = '/home';
                    } else {
                        // 로그아웃 실패 시 처리
                        alert('로그아웃에 실패하였습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

        // 로그인/로그아웃 상태 업데이트
        checkLoginStatus();
    });

    // 페이지 로드 시 로그인 상태 체크
    checkLoginStatus();
});
