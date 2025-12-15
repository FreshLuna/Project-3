<script>
  export let count;

  // Reactive computed text
  $: spotText = count <= 0
    ? "Venteliste"
    : count < 5
    ? `Kun ${count} pladser tilbage`
    : `pladser tilbage: ${count}`;

  // Reactive computed background color
 $: bgColor = count <= 0
    ? "blue"
    : count <= 1
    ? "red"
    : count < 5
    ? "orange"
    : "green";
</script>

<style>
  .spot-box {
    position: absolute;
    bottom: 0px;   /* Adjust as needed */
    left: 0px;
    z-index: 10;
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-bottom-left-radius: 0.75rem;
    font-weight: bold;
    font-size: 0.85rem;
    color: var(--color-white);
    text-align: center;
}
</style>

<div class="spot-box" style="background-color: {bgColor}">
  {spotText}
</div>
