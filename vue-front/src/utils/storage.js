export function getStorageUser() {
    const userStr = localStorage.getItem('user');
    if (!userStr) return null;
    try {
        return JSON.parse(userStr);
    } catch (e) {
        console.error("localStorage.user 解析失败", e);
        return null;
    }
}

export function getStorageToken() {
    return localStorage.getItem('token');
}

export function setStorageLogin(data) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify(data.user));
}

export function clearStorageLogin() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
}
