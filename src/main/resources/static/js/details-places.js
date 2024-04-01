// 모달 창 요소 가져오기
var modal = document.getElementById('exampleModal');

// 모달 열기 함수
function openModal() {
    modal.style.display = 'block';
}

// 모달 닫기 함수
function closeModal() {
    modal.style.display = 'none';
}

// 닫기 버튼 요소 가져오기
var closeButton = document.getElementsByClassName('btn-close')[0];

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

// 여행지 상세 정보 API 호출 및 표시
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
    var modalBody = document.querySelector('.modal-body');
    var modalHeader = document.querySelector('.modal-header');

    if (modalBody !== null && modalHeader !== null) {
        modalHeader.innerHTML = '';
        modalBody.innerHTML = '';
    }

    // 상세 정보 표시
    details.forEach(item => {
        if (item.title) {
            var titleElement = document.createElement('h3');
            titleElement.textContent = item.title; // 제목 설정
            // titleElement.classList.add('modal-title'); // modal-title 클래스 추가
            modalHeader.appendChild(titleElement); // 모달 바디에 추가
        }

        // 이미지 표시 가능
        if (item.firstimage) {
            var imageElement = document.createElement('img');
            imageElement.src = item.firstimage;
            modalBody.appendChild(imageElement);
        }

        if (item.overview) {
            var overviewElement = document.createElement('p');
            overviewElement.textContent = item.overview;
            modalBody.appendChild(overviewElement);
        }

        if (item.tel) {
            var telElement = document.createElement('p');
            telElement.textContent = '전화번호: ' + item.tel;
            modalBody.appendChild(telElement);
        }

        if (item.homepage) {
            var homepageElement = document.createElement('p');
            homepageElement.innerHTML = '홈페이지: ' + item.homepage;
            modalBody.appendChild(homepageElement);
        }

    });

    // 모달 열기
    openModal();
}

