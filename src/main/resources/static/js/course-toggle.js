function toggleButton() {
    const extraContent = document.querySelector('.extra-content');
    const courseForm = document.getElementById('courseForm');
    const selectedPlacesList = document.getElementById('selected-places-list');
    const courseCreateBtn = document.querySelector('.course-create-btn');

    if (extraContent.style.width === '300px') {
        extraContent.style.width = '0';
        courseForm.style.display = 'none';
        selectedPlacesList.style.display = 'none';
    } else {
        extraContent.style.width = '300px';
        courseForm.style.display = 'block';
        selectedPlacesList.style.display = 'block';
    }
}

let isToggleOpen = false;

function toggle() {
    const extraContent = document.querySelector('.extra-content');
    const courseForm = document.getElementById('courseForm');
    const selectedPlacesList = document.getElementById('selected-places-list');
    const courseCreateBtn = document.querySelector('.course-create-btn');
    const toggleButton = document.querySelector('.btn');

    if (isToggleOpen) {
        extraContent.style.width = '0';
        courseForm.style.display = 'none';
        selectedPlacesList.style.display = 'none';
        courseCreateBtn.style.display = 'none';
        toggleButton.innerHTML = '<i class="bi bi-chevron-right"></i>';
    } else {
        extraContent.style.width = '300px';
        courseForm.style.display = 'block';
        selectedPlacesList.style.display = 'block';
        courseCreateBtn.style.display = 'block';
        toggleButton.innerHTML = '<i class="bi bi-chevron-left"></i>';
    }

    // 토글 상태 변경
    isToggleOpen = !isToggleOpen;
}