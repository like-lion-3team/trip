function courseLoad() {
    fetch('/api/v1/courses/' + [[${courseId}]], {
        method: 'GET'
    }).then(response => {
        if (!response.ok) {
            throw new Error('게시글을 불러오는데 실패했습니다.');
        }
        return response.json(); // JSON 형태로 변환하여 반환
    }).then(data => {
        document.getElementById('courseTitle').innerText = data.title;
        document.getElementById('courseDesc').innerText = data.desc;
    })
}

courseLoad();