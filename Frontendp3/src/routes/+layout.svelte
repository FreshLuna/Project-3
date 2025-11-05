<script lang="ts">
    let { children } = $props();
	import logo from '$lib/assets/logo.png';

</script>

<style>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  background-color: #6E479B;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

ul .logo img {
  max-width: 100px;
  padding: 0px 15px;
}

ul .nav-links {
  display: flex;
  height: 100%;   
}

ul .nav-links a {
  display: block;
  color: white;
  text-decoration: none;
  padding: 25px 25px;
  font-size: 20px;
  font-weight: bold;
}

ul .nav-links a:hover {
  background-color: #5e3b85ff;
}
</style>

<nav>
  <ul>
    <li class="logo">
      <a href="/"><img alt="The project logo" src={logo} /></a>
    </li>

	<li class="nav-links">
    	<a href="/login" class="nav-link">Login</a>
    	<a href="/opret" class="nav-link">Opret Organisation</a>
	</li>

  </ul>
</nav>

{@render children()}
