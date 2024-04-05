async function populateCourseData(courseId) {
    try {
        const response = await fetch(`/api/v1/courses/${courseId}`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('API 요청이 실패했습니다.');
        }

        const courseData = await response.json();
        console.log(courseData);

        // 선택한 여행지 목록에 추가
        selectedPlaces = courseData.places;

        // 선택한 여행지 목록을 화면에 표시
        renderSelectedPlaces();

        // 가져온 좌표값으로 마커를 표시합니다
        displayMarkers();

        moveToSelectedPlace(courseData.places[0]);

        document.getElementById('courseTitle').value = courseData.title;
        document.getElementById('courseDesc').value = courseData.desc;
    } catch (error) {
        console.error('코스 데이터를 가져오는 중 오류가 발생했습니다:', error);
    }
}

window.onload = function() {
    // 현재 URL 가져오기
    const currentUrl = window.location.href;

    // URL에서 마지막 부분 추출하기
    const lastSegment = currentUrl.substring(currentUrl.lastIndexOf('/') + 1);

    // 숫자인지 확인하기
    const courseId = parseInt(lastSegment);

    // courseId 가 NaN 이 아닌지 확인
    if (!isNaN(courseId)) {
        // courseId 가 유효한 경우 populateCourseData 함수 호출
        populateCourseData(courseId);
    } else {
        console.error('유효한 courseId 가 없습니다.');
    }
};