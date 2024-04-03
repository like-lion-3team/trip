let currentPage = 1; // 현재 페이지 초기화
let totalPages = 0; // 총 페이지 수 초기화


// 여행지 검색 함수
function searchPlaces() {
    // 1. 사용자가 입력한 검색어 가져오기
    const searchQuery = document.getElementById('search').value;

    // 2. 검색어가 빈 문자열인지 확인
    if (searchQuery.trim() !== '') {
        // 검색어가 비어있지 않을 경우 API 요청 보내기
        fetch(`/api/v1/places/search?keyword=${searchQuery}&pageNo=${currentPage}`, {
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
                displaySearchResults(data);
                // 총 페이지 수 업데이트
                totalPages = Math.ceil(data.response.body.totalCount / 12);
                // 페이지 번호 표시 업데이트
                updatePageNumbers();
            })
            .catch(error => {
                // API 요청이 실패했을 때 에러 처리
                alert(error.message);
                console.error('Error:', error);
            });
    }
}

// 전역 변수로 지역 코드와 시군구 코드를 저장할 변수 선언
let selectedAreaCode = '';
let selectedSigunguCode = '';
let selectedContentTypeId = '';


// 광역시/도 코드 조회
function getAreaCode() {
    fetch(`/api/v1/places/area-code`, {
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
            displayAreaCode(data);
        })
        .catch(error => {
            // API 요청이 실패했을 때 에러 처리
            alert(error.message);
            console.error('Error:', error);
        });
}

// 광역시/도 화면 표시
function displayAreaCode(data) {
    const areaCodeModalBody = document.querySelector('#areaSelectModal .modal-body');
    areaCodeModalBody.innerHTML = ''; // 모달창 내용 초기화

    // 리스트 그룹 요소 생성
    const listGroup = document.createElement('div');
    listGroup.classList.add('list-group');

    // 각 지역 코드 항목 추가
    data.response.body.items.item.forEach(item => {
        const code = item.code;
        const name = item.name;

        const areaOption = document.createElement('button');
        areaOption.type = 'button';
        areaOption.classList.add('btn', 'btn-light', 'w-100', 'mb-2');
        areaOption.textContent = name;
        // 클릭 이벤트 추가
        areaOption.addEventListener('click', function() {
            // 선택한 지역 코드를 해당 HTML 요소에 채워 넣기
            document.getElementById('area-name').value = name;
            selectedAreaCode = code;
            getSigunguCode(selectedAreaCode); // 해당 지역 코드를 파라미터로 전달하여 getSigunguCode 함수 호출
        });
        // 해당 버튼에 데이터 코드 속성 추가
        areaOption.setAttribute('data-code', code);
        listGroup.appendChild(areaOption);
    });

    // 리스트 그룹을 모달 바디에 추가
    areaCodeModalBody.appendChild(listGroup);
}


// 시/군/구 코드 조회
function getSigunguCode(selectedAreaCode) {
    fetch(`/api/v1/places/area-code?areaCode=${selectedAreaCode}`, {
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
            displaySigunguCode(data);
        })
        .catch(error => {
            // API 요청이 실패했을 때 에러 처리
            alert(error.message);
            console.error('Error:', error);
        });
}

// 시군구 화면 표시
function displaySigunguCode(data) {
    const sigunguModalBody = document.querySelector('#areaSelectModal .modal-body');
    sigunguModalBody.innerHTML = ''; // 모달창 내용 초기화

    // 리스트 그룹 요소 생성
    const listGroup = document.createElement('div');
    listGroup.classList.add('list-group');

    // 각 시군구 코드 항목 추가
    data.response.body.items.item.forEach(item => {
        const code = item.code;
        const name = item.name;

        const sigunguOption = document.createElement('button');
        sigunguOption.type = 'button';
        sigunguOption.classList.add('btn', 'btn-light', 'w-100', 'mb-2');
        sigunguOption.textContent = name;
        // 클릭 이벤트 추가
        sigunguOption.addEventListener('click', function() {
            // 시군구 코드를 해당 HTML 요소에 채워 넣기
            document.getElementById('sigungu-name').value = name;
            selectedSigunguCode = code;

            // 모달 닫기
            const modal = document.querySelector('#areaSelectModal');
            const modalInstance = bootstrap.Modal.getInstance(modal);
            modalInstance.hide();
        });
        // 해당 버튼에 데이터 코드 속성 추가
        sigunguOption.setAttribute('data-code', code);
        listGroup.appendChild(sigunguOption);
    });
    // 리스트 그룹을 모달 바디에 추가
    sigunguModalBody.appendChild(listGroup);
}


// 관광 타입 정보를 저장할 객체
const contentTypes = {
    '관광지': '12',
    '문화시설': '14',
    '행사/공연/축제': '15',
    '레저 스포츠': '28',
    '숙박': '32',
    '쇼핑': '38',
    '음식점': '39'
};

// 모달창에 관광 타입 정보를 표시하는 함수
function displayContentTypes() {
    const modalBody = document.querySelector('#contentSelectModal .modal-body');
    modalBody.innerHTML = ''; // 모달창 내용 초기화

    const listGroup = document.createElement('div');
    listGroup.classList.add('list-group');

    Object.entries(contentTypes).forEach(([typeName, typeId]) => {

        const button = document.createElement('button');
        button.type = 'button';
        button.classList.add('btn', 'btn-light', 'w-100', 'mb-2');
        button.textContent = typeName;
        button.addEventListener('click', function() {
            document.getElementById('content-type').value = typeName;
            // 선택한 관광 타입 정보를 전역 변수에 저장
            selectedContentTypeId = typeId;

            // 모달 닫기
            const modal = document.querySelector('#contentSelectModal');
            const modalInstance = bootstrap.Modal.getInstance(modal);
            modalInstance.hide();
        });

        listGroup.appendChild(button);
    });

    modalBody.appendChild(listGroup);
}



// 지역 기반 여행지 조회
function areaBasedPlaces() {
    console.log(selectedAreaCode + ' ' + selectedSigunguCode + ' ' + selectedContentTypeId);
    // 검색어가 비어있지 않을 경우 API 요청 보내기
    fetch(`/api/v1/places/course-list?&pageNo=${currentPage}&areaCode=${selectedAreaCode}&sigunguCode=${selectedSigunguCode}&contentTypeId=${selectedContentTypeId}`, {
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
            displaySearchResults(data);
            // 총 페이지 수 업데이트
            totalPages = Math.ceil(data.response.body.totalCount / 12);
            // 페이지 번호 표시 업데이트
            updatePageNumbers();
        })
        .catch(error => {
            // API 요청이 실패했을 때 에러 처리
            alert(error.message);
            console.error('Error:', error);
        });
}

// 페이지 번호 표시 업데이트 함수
function updatePageNumbers() {
    const pageNumbersContainer = document.getElementById('page-numbers');
    pageNumbersContainer.innerHTML = ''; // 이전에 있던 페이지 번호 제거

    let startPage = 1;
    let endPage = totalPages;

    // 페이지 간격이 6일 때
    if (totalPages > 6) {
        startPage = Math.max(1, currentPage - 2);
        endPage = Math.min(totalPages, currentPage + 3);

        if (endPage === totalPages) {
            startPage = totalPages - 5;
        } else if (startPage === 1) {
            endPage = 6;
        }
    }

    // 이전 페이지로 이동하는 화살표 추가
    if (currentPage > 1) {
        const prevPageButton = document.createElement('li');
        prevPageButton.classList.add('page-item');
        const link = document.createElement('button');
        link.textContent = '←';
        link.classList.add('page-link');
        link.addEventListener('click', function() {
            currentPage--;
            searchPlaces();
            areaBasedPlaces();
        });
        prevPageButton.appendChild(link);
        pageNumbersContainer.appendChild(prevPageButton);
    }

    for (let i = startPage; i <= endPage; i++) {
        const pageNumberButton = document.createElement('li');
        pageNumberButton.classList.add('page-item');
        const link = document.createElement('button');
        link.textContent = i;
        link.classList.add('page-link');
        link.addEventListener('click', function() {
            currentPage = i;
            searchPlaces();
            areaBasedPlaces();
        });
        pageNumberButton.appendChild(link);
        pageNumbersContainer.appendChild(pageNumberButton);
    }

    // 다음 페이지로 이동하는 화살표 추가
    if (currentPage < totalPages) {
        const nextPageButton = document.createElement('li');
        nextPageButton.classList.add('page-item');
        const link = document.createElement('button');
        link.textContent = '→';
        link.classList.add('page-link');
        link.addEventListener('click', function() {
            currentPage++;
            searchPlaces();
            areaBasedPlaces();
        });
        nextPageButton.appendChild(link);
        pageNumbersContainer.appendChild(nextPageButton);
    }
}




// 초기 페이지 로드 시 검색 수행
searchPlaces();

// 검색 결과를 화면에 표시하는 함수
function displaySearchResults(results) {
    const searchResultsList = document.getElementById('search-results');
    searchResultsList.innerHTML = ''; // 이전 결과 지우기

    // 응답 데이터에서 각 여행지의 제목과 상세정보 버튼을 추출하여 리스트에 추가
    results.response.body.items.item.forEach(item => {
        const listItem = document.createElement('li');
        // 이미지 크기를 100 x 75로 조정하고, float 및 여백 설정 추가
        listItem.innerHTML =
        `<div class="card">
            <div class="row g-0">
                <div class="col-md-3 d-flex align-items-center" style="margin-left: 10px">
                    <!-- 이미지가 카드 이미지 부분에 꽉 차게 설정 -->
                    <img src="${item.firstimage}" class="card-img img-square" alt="여행지 이미지"> 
                </div>
                <div class="col-md-6">
                    <div class="card-body">
                         <h6 class="card-title" style="font-size: 13px;  cursor: pointer;" onclick="getPlaceDetails('${item.contentid}')" data-bs-toggle="modal" data-bs-target="#exampleModal">${item.title}</h6>
                        <p class="card-text" style="font-size: 0.65rem; color: gray;">${item.addr1} ${item.addr2}</p> <!-- 주소 글씨를 회색으로 변경 -->
                    </div>
                </div>
                <div class="col-md-2 d-flex align-items-center">
                    <!-- 코스에 추가 버튼을 부트스트랩 아이콘 +로 대체 -->
                    <button onclick="addToCourse('${item.contentid}')" class="btn btn-lg"><i class="bi bi-plus-circle-fill"></i></button>
                </div>
            </div>
        </div>
    `;

        // 생성한 항목을 검색 결과 리스트에 추가
        searchResultsList.appendChild(listItem);
    });

    // 총 결과 수와 결과 범위 업데이트
    if (results.response.body.totalCount > 0) {
        const totalResults = results.response.body.totalCount;
        const startResult = (currentPage - 1) * 12 + 1;
        const endResult = Math.min(currentPage * 12, totalResults);
        document.getElementById('total-results').textContent = totalResults;
        document.getElementById('start-result').textContent = startResult;
        document.getElementById('end-result').textContent = endResult;
    } else {
        // 결과가 없을 경우 결과 수를 숨깁니다.
        document.getElementById('page-info').style.display = 'none';
    }
}



