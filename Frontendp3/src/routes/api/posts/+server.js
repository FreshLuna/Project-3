
export const GET = () => {
    fetch('https://localhost:5173');

    return new Response(JSON.stringify({message: "Hello" }), {status: 200 });
}