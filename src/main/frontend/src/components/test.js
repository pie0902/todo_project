import Login from "./login";

function Test() {
    const handleSubmit = async (e) => {
      e.preventDefault();
      const token = localStorage.getItem('token'); // 로컬 스토리지에서 토큰을 가져옵니다.

      // 토큰이 없는 경우, 추가 로직을 실행하거나 오류 처리
      if (!token) {
        console.error("토큰이 없습니다. 로그인이 필요합니다.");
        return; // 혹은 사용자를 로그인 페이지로 리다이렉션
      }

      try {
        const response = await fetch('/test', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`, // 헤더에 토큰을 추가합니다.
            'Content-Type': 'application/json',
          },
        });
        const data = await response.json();
        if (response.ok) {
          console.log('테스트 성공:', data);
          // 회원가입 성공 후 처리 (예: 로그인 페이지로 리다이렉트)
        } else {
          throw new Error(data.message || '회원가입 실패');
        }
      } catch (error) {
        console.error('회원가입 에러:', error);
      }
    };

  return (
      <button onClick={handleSubmit}>테스트 요청 보내기</button>
  );
}
export default Test;