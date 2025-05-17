import { GoogleLogin } from '@react-oauth/google';

export default function Login() {
    let API;
    return (
        <GoogleLogin
            onSuccess={() => window.location.href = `${API}/oauth2/authorization/google`}
            useOneTap
        />
    );
}