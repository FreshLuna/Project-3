<script>
  export let count;

  // Reactive computed text
  $: spotText = count <= 0
    ? "kom på venteliste"
    : count < 5
    ? `only ${count} spots left`
    : `pladser tilbage: ${count}`;

  // Reactive computed background color
 $: bgColor = count <= 0
    ? "blue"
    : count <= 1
    ? "red"
    : count < 5
    ? "yellow"
    : "green";
</script>

<style>
  .spot-box {
    top: 8px;   /* Adjust as needed */
    right: 8px;
    z-index: 10;
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 0.5rem;
    font-weight: bold;
    font-size: 0.85rem;
    color: white;
    min-width: 100px;
    text-align: center;
}
</style>

<div class="spot-box" style="background-color: {bgColor}">
  {spotText}
</div>
