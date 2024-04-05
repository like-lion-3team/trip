// 여행지를 저장할 배열 초기화
let selectedPlaces = [];

// 마커 객체를 저장할 배열 초기화
let markers = [];

// 여행지를 선택하여 배열에 추가하는 함수
function addPlace(place) {
    selectedPlaces.push(place);
}

// "코스에 추가" 버튼 클릭 시 호출되는 함수
function addToCourse(contentId) {
    // 이미 선택한 여행지인지 확인
    const isAlreadySelected = selectedPlaces.some(selectedPlace => selectedPlace.contentId === contentId);
    // 이미 선택한 여행지가 아니라면 정보를 가져와서 배열에 추가
    if (!isAlreadySelected) {
        // API 호출하여 여행지의 정보 가져오기
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
                // 여행지의 정보를 가져와서 선택한 여행지 배열에 추가
                const place = {
                    contentId: contentId,
                    title: data.response.body.items.item[0].title,
                    lng: parseFloat(data.response.body.items.item[0].mapx),
                    lat: parseFloat(data.response.body.items.item[0].mapy),
                    address: data.response.body.items.item[0].addr1 + " " + data.response.body.items.item[0].addr2,
                    thumbnailUrl: data.response.body.items.item[0].firstimage
                };
                addPlace(place);

                // 선택한 여행지 목록을 화면에 표시
                renderSelectedPlaces();

                // 가져온 좌표값으로 마커를 표시합니다
                displayMarkers();

                // 선택한 여행지의 좌표로 지도의 중심 이동 및 확대
                moveToSelectedPlace(place);
            })
            .catch(error => {
                // API 요청이 실패했을 때 에러 처리
                alert(error.message);
                console.error('Error:', error);
            });
        toggle();
    } else {
        // 이미 선택한 여행지라면 경고 메시지 표시
        alert('이미 선택한 여행지입니다.');
    }
}

// "X" 버튼을 클릭하여 선택한 여행지를 삭제하는 함수
function removePlace(contentId) {
    // 선택한 여행지 배열에서 해당 contentId를 가진 여행지 제거
    selectedPlaces = selectedPlaces.filter(place => place.contentId !== contentId);
    // 변경된 선택한 여행지 목록을 다시 화면에 표시
    renderSelectedPlaces();

    // 해당 contentId를 가진 마커를 찾아서 제거
    markers = markers.filter(marker => {
        if (marker.contentId === contentId) {
            marker.setMap(null); // 해당 마커를 지도에서 제거
            return false; // 마커 배열에서 제거
        }
        return true; // 유지되어야 하는 마커는 다시 배열에 추가
    });

    // 마커 업데이트
    displayMarkers();
}

// 여행지의 좌표를 지도에 마커로 표시하는 함수
function displayMarkers(coordinates) {
    markers.forEach(marker => {
        marker.setMap(null);
    })
    markers = [];
    // 선택한 여행지 배열을 순회하면서 각각의 좌표에 마커를 표시
    selectedPlaces.forEach(place => {
        var position = new naver.maps.LatLng(place.lat, place.lng);
        var marker = new naver.maps.Marker({
            position: position,
            map: map
        });
        // 생성된 마커를 markers 배열에 추가
        markers.push(marker);
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
        listItem.classList.add("list-item"); // CSS 클래스 추가

        // 삭제 버튼 추가
        let removeButton = document.createElement("button");
        removeButton.textContent = "X";
        removeButton.classList.add("remove-button"); // CSS 클래스 추가

        // 삭제 버튼에 이벤트 리스너 추가
        removeButton.addEventListener("click", () => {
            // 해당 여행지를 선택한 여행지 배열에서 삭제
            removePlace(place.contentId);
        });

        // 리스트 아이템에 삭제 버튼 추가
        listItem.appendChild(removeButton);

        // 리스트 아이템을 목록에 추가
        selectedPlacesList.appendChild(listItem);

        // 해당 여행지를 클릭했을 때 지도의 중심을 해당 위치로 이동
        listItem.addEventListener("click", () => {
            moveToSelectedPlace(place);
        });
    });

    // 선택한 여행지 배열을 콘솔에 출력
    console.log(selectedPlaces);
}


// 코스 생성 또는 수정 버튼 클릭 시 호출되는 함수
async function updateOrCreateCourse() {
    const currentUrl = window.location.href;
    const courseId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1));
    const title = document.getElementById('courseTitle').value;
    const desc = document.getElementById('courseDesc').value;

    // 코스 정보가 입력되어 있는지 확인
    if (title.trim() === "" || desc.trim() === "") {
        alert("코스 제목과 설명을 입력하세요.");
        return;
    }

    // 선택한 여행지가 있는지 확인
    if (selectedPlaces.length === 0) {
        alert("최소 한 개의 여행지를 선택하세요.");
        return;
    }

    try {
        // 현재 사용자의 memberId를 가져옴
        const currentMemberId = await getCurrentMemberId();

        if (!currentMemberId) {
            // 로그인이 필요한 경우 로그인 페이지로 리다이렉트
            alert('로그인이 필요합니다.');
            window.location.href = '/login'; // 로그인 페이지로 리다이렉트
            return;
        }

        let response;
        if (currentUrl.includes("/api/v1/courses/course-update")) {
            // 현재 URL이 코스 수정 페이지인 경우
            response = await fetch(`/api/v1/courses/${courseId}`, {
                method: 'PUT',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    title: title,
                    desc: desc,
                    places: selectedPlaces
                })
            });
        } else if (currentUrl.includes("/api/v1/courses/course-create")) {
            // 현재 URL이 코스 생성 페이지인 경우
            response = await fetch("/api/v1/courses", {
                method: "POST",
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token'),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    title: title,
                    desc: desc,
                    places: selectedPlaces
                })
            });
        }

        if (!response.ok) {
            throw new Error('API 요청이 실패했습니다.');
        }

        // 성공 시 홈 페이지로 이동 또는 어떤 동작을 수행할 수 있습니다.
        window.location.href = '/my-page';
    } catch (error) {
        console.error('코스 정보를 처리하는 중 오류가 발생했습니다:', error);
        alert(error.message);
    }
}


// 여행지의 좌표로 지도의 중심 이동 및 확대하는 함수
function moveToSelectedPlace(place) {
    // 선택한 여행지의 좌표로 지도의 중심 이동
    map.setCenter(new naver.maps.LatLng(place.lat, place.lng));

    // 지도 확대 수준 조정 (예: 현재 줌 레벨 + 2)
    // const currentZoomLevel = map.getZoom();
    // const newZoomLevel = currentZoomLevel + 1; // 적절한 값을 설정할 수 있습니다.
    // map.setZoom(newZoomLevel);
    map.setZoom(18);
}