<div>
<h1>Welcome to Aalborg Try Out</h1>
<p>Visit <a href="https://motivu.dk/aalborg/aalborgtryout"></a> to read the documentation</p>

</div>

<script>
  import { onMount } from "svelte";

  let message = "";

  onMount(async () => {
    console.log('the component has mounted')
    const res = await fetch("http://localhost:8081/echo", {
      method: "POST",
      body: "Hello Java!"
    });
    message = await res.text();
  });
</script>

<h1>{message}</h1>
