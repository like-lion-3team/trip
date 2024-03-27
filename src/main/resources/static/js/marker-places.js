// 여행지를 저장할 배열 초기화
let selectedPlaces = [];

// 여행지를 선택하여 배열에 추가하는 함수
function addPlace(place) {
    // 선택한 여행지를 배열에 추가
    selectedPlaces.push(place);
}

// "코스에 추가" 버튼 클릭 시 호출되는 함수
function addToCourse(contentId) {
    // API 호출하여 여행지의 정보 가져오기
    fetch(`/api-test/detail?contentId=${contentId}`)
        .then(response => {
            if (!response.ok) {
                // 응답이 성공적이지 않을 때 에러 처리
                throw new Error('API 요청이 실패했습니다.');
            }
            // JSON 형태로 변환하여 반환
            return response.json();
        })
        .then(data => {
            // 여행지의 정보를 가져와서 선택한 여행지 배열에 추가
            const place = {
                contentId: contentId,
                title: data.response.body.items.item[0].title
            };
            addPlace(place);

            // 선택한 여행지 목록을 화면에 표시
            renderSelectedPlaces();

            // 여행지 좌표를 가져와 지도에 마커 표시
            const coordinates = [{
                mapx: parseFloat(data.response.body.items.item[0].mapx),
                mapy: parseFloat(data.response.body.items.item[0].mapy)
            }];
            // 가져온 좌표값으로 마커를 표시합니다
            displayMarkers(coordinates);
        })
        .catch(error => {
            // API 요청이 실패했을 때 에러 처리
            alert(error.message);
            console.error('Error:', error);
        });

}


// 선택한 여행지를 화면에 표시하는 함수
function renderSelectedPlaces() {
    let selectedPlacesList = document.getElementById("selected-places-list");
    // 기존에 표시되었던 여행지 목록 초기화
    selectedPlacesList.innerHTML = "";

    // 선택한 여행지 배열을 순회하면서 각각의 여행지를 화면에 추가
    selectedPlaces.forEach(place => {
        let listItem = document.createElement("li");
        listItem.textContent = place.title; // 여행지 이름 등 필요한 정보를 여기서 표시
        selectedPlacesList.appendChild(listItem);
    });
}

// 여행지의 좌표를 지도에 마커로 표시하는 함수
function displayMarkers(coordinates) {
    // 새로운 마커들을 추가합니다
    coordinates.forEach(coord => {
        var position = new naver.maps.LatLng(coord.mapy, coord.mapx);
        var marker = new naver.maps.Marker({
            position: position,
            map: map
        });
    });
}
