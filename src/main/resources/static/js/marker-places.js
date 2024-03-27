// "코스에 추가" 버튼 클릭 시 호출되는 함수
function addToCourse(contentId) {
    // 추가되지 않은 경우 해당 여행지의 좌표를 가져와 마커를 표시하고 상태를 업데이트
    getPlaceCoordinates(contentId);
}

// 여행지의 좌표를 가져오는 함수
function getPlaceCoordinates(contentId) {
    // API 호출하여 여행지의 좌표값을 가져옵니다
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
            // API 응답 데이터에서 여행지의 좌표값을 가져와서 배열에 저장합니다
            const places = data.response.body.items.item;
            const coordinates = places.map(place => ({
                mapx: parseFloat(place.mapx),
                mapy: parseFloat(place.mapy)
            }));
            // 가져온 좌표값으로 마커를 표시합니다
            displayMarkers(coordinates);
        })
        .catch(error => {
            // API 요청이 실패했을 때 에러 처리
            alert(error.message);
            console.error('Error:', error);
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
