var mapOptions = {
    center: new naver.maps.LatLng(37.3595704, 127.105399),
    zoom: 15
};

var map = new naver.maps.Map('map', mapOptions);


// 여행지 선택 함수
function selectPlace(placeId) {
    // 선택한 여행지를 리스트에 추가합니다
    const selectedPlacesList = document.getElementById('selected-places');
    const placeName = ''; // 선택한 여행지의 이름
    const listItem = document.createElement('li');
    listItem.textContent = placeName;
    selectedPlacesList.appendChild(listItem);
}

// 지도 표시 함수
function displayMap() {
    // 지도를 표시하는 기능을 구현합니다
}

// 페이지 로드 시 초기화 작업을 수행합니다
window.onload = function() {
    // 지도를 초기화합니다
    displayMap();
};