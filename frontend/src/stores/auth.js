import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
    const isLoggedIn = ref(false)
    const currentUser = ref(null)

    async function checkSession() {
        try {
            const res = await fetch('http://localhost:8080/user', {
                credentials: 'include'
            })

            console.log('res', res)

            if (res.ok) {
                currentUser.value = await res.json()
                isLoggedIn.value = true
            } else {
                isLoggedIn.value = false
                currentUser.value = null
            }
        } catch (err) {
            console.error('Session check failed:', err)
        }
    }

    // async function logout() {
    //     try {
    //         await fetch('http://localhost:8080/logout', {
    //             method: 'POST',
    //             credentials: 'include'
    //         })
    //     } catch (err) {
    //         console.error('Logout failed:', err)
    //     }
    //     isLoggedIn.value = false
    //     currentUser.value = null
    // }

    return {
        isLoggedIn,
        currentUser,
        checkSession
    }
})