// 여행지 상세 정보 API 호출
function getPlaceDetails(contentId) {
    // API 호출
    fetch(`/api/v1/places/detail?contentId=${contentId}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (!response.ok) {
                // 응답이 성공적이지 않을 때 에러 처리
                throw new Error('API 요청이 실패했습니다.');
            }
            // JSON 형태로 변환하여 반환
            return response.json();
        })
        .then(data => {
            // API 응답 데이터를 화면에 표시
            displayPlaceDetails(data.response.body.items.item);
        })
        .catch(error => {
            // API 요청이 실패했을 때 에러 처리
            alert(error.message);
            console.error('Error:', error);
        });
}

// 상세 정보 표시 함수
function displayPlaceDetails(details) {
    // 모달 바디 요소 가져오기
    var modalBody = document.getElementById('modal-body');

    // 모달 바디 초기화
    modalBody.innerHTML = '';

    // 상세 정보 표시
    details.forEach(item => {
        var titleElement = document.createElement('h3');
        titleElement.textContent = item.title;
        modalBody.appendChild(titleElement);

        var overviewElement = document.createElement('p');
        overviewElement.textContent = item.overview;
        modalBody.appendChild(overviewElement);

        // 추가적인 상세 정보 표시 가능
        var telElement = document.createElement('p');
        telElement.textContent = '전화번호: ' + item.tel;
        modalBody.appendChild(telElement);

        var homepageElement = document.createElement('p');
        homepageElement.innerHTML = '홈페이지: ' + item.homepage;
        modalBody.appendChild(homepageElement);

        // 이미지 표시 가능
        var imageElement = document.createElement('img');
        imageElement.src = item.firstimage;
        modalBody.appendChild(imageElement);
    });

    // 모달 열기
    openModal();
}

// 모달 창 요소 가져오기
var modal = document.getElementById('myModal');

// 모달 열기 함수
function openModal() {
    modal.style.display = 'block';
}

// 닫기 버튼 요소 가져오기
var closeButton = document.getElementsByClassName('close')[0];

// 모달 닫기 함수
function closeModal() {
    modal.style.display = 'none';
}

// 닫기 버튼에 클릭 이벤트 핸들러 추가
closeButton.onclick = function() {
    closeModal();
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    if (event.target === modal) {
        closeModal();
    }
}
