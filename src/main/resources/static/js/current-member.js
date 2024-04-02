function getCurrentMemberId() {
    return fetch('/api/v1/auth/current-member', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }).then(response => {
        if (response.ok) {
            return response.json();
        }
    }).catch(error => {
            console.log(error);
        })
}