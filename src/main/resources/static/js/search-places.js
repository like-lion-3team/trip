let currentPage = 1; // 현재 페이지 초기화
let totalPages = 0; // 총 페이지 수 초기화

// 여행지 검색 함수
function searchPlaces() {
    // 1. 사용자가 입력한 검색어 가져오기
    const searchQuery = document.getElementById('search').value;

    // 2. 검색어가 빈 문자열인지 확인
    if (searchQuery.trim() === '') {
        // 검색어가 비어있을 경우 알람 표시
        alert("검색어를 입력하세요.");
    } else {
        // 검색어가 비어있지 않을 경우 API 요청 보내기
        fetch(`/api/v1/places/search?keyword=${searchQuery}&pageNo=${currentPage}`)
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
        const prevPageButton = document.createElement('button');
        prevPageButton.textContent = '←';
        prevPageButton.addEventListener('click', function() {
            currentPage--;
            searchPlaces();
        });
        pageNumbersContainer.appendChild(prevPageButton);
    }

    for (let i = startPage; i <= endPage; i++) {
        const pageNumberButton = document.createElement('button');
        pageNumberButton.textContent = i;
        pageNumberButton.addEventListener('click', function() {
            currentPage = i;
            searchPlaces();
        });
        pageNumbersContainer.appendChild(pageNumberButton);
    }

    // 다음 페이지로 이동하는 화살표 추가
    if (currentPage < totalPages) {
        const nextPageButton = document.createElement('button');
        nextPageButton.textContent = '→';
        nextPageButton.addEventListener('click', function() {
            currentPage++;
            searchPlaces();
        });
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
        listItem.innerHTML = `
            <img src="${item.firstimage}" alt="여행지 이미지" style="width: 100px; height: 75px; object-fit: cover; float: left; margin-right: 10px;"> <!-- 이미지 크기 조정 -->
            <div>
                <span>${item.title}</span> 
                <p style="font-size: 0.8em; color: gray;">${item.addr1} ${item.addr2}</p> <!-- 주소 글씨를 회색으로 변경 -->
                <button onclick="getPlaceDetails('${item.contentid}')">상세정보</button> 
                <button class="add-to-course-button" onclick="addToCourse('${item.contentid}')">코스에 추가</button>
            </div>`;

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

