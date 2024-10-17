document.addEventListener("DOMContentLoaded", function () {
    fetchCurrentUser();
});

function fetchCurrentUser() {
    fetch('/api/user')
        .then(response => {
            if (!response.ok) {
                throw new Error('Не удалось получить пользователя');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);

            document.getElementById('navbarFirstName').textContent = data.firstName;
            document.getElementById('navbarRoles').textContent = data.roles.map(role => role.name.replace('ROLE_', '')).join(', ');

            document.getElementById('id').textContent = data.id;
            document.getElementById('firstName').textContent = data.firstName;
            document.getElementById('lastName').textContent = data.lastName;
            document.getElementById('age').textContent = data.age;
            document.getElementById('roles').textContent = data.roles.map(role => role.name.replace('ROLE_', '')).join(', ');
        })
        .catch(error => {
            console.error('Oshibka fetchinga: ', error);
        });
}