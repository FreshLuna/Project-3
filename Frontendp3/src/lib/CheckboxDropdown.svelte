<script lang="ts">
    import { onDestroy, onMount } from "svelte";
    import type { Writable } from "svelte/store";
    export let store: Writable<{ id: number; label: string; checked: boolean }[]>;
    export let label: string;

    $: hasChecked = $store.some((item) => item.checked);
    let open = false;
    let dropdownRef: HTMLElement;

    function toggleItem(id: number) {
        store.update((list) =>
            list.map((item) =>
                item.id === id // please please please make this a real if
                    ? { ...item, checked: !item.checked }
                    : item,
            ),
        );
    }

    // script: clear all checkboxes
    function clearAll() {
        store.update((list) =>
            list.map((item) => ({ ...item, checked: false })),
        );
    }

    // script: close dropdown when clicking outisde
    function closeDropdownOnClickOutside(event: MouseEvent) {
        if (dropdownRef && !dropdownRef.contains(event.target as Node)) {
            open = false;
        }
    }

    // // event listeners for closing dropdowns
    onMount(() => {
        if (typeof document !== "undefined") {
            document.addEventListener("click", closeDropdownOnClickOutside);
        }
    });

    onDestroy(() => {
        if (typeof document !== "undefined") {
            document.removeEventListener("click", closeDropdownOnClickOutside);
        }
    });
</script>

<div class="dropdown" bind:this={dropdownRef}>
    <button
        type="button"
        class="dropdownButton"
        class:active={hasChecked}
        on:click={() => (open = !open)}
        >{label}
    </button>

    {#if open}
        <div class="menu">
            <button type="button" class="clear-btn" on:click={clearAll}>
                Fjern filtre
            </button>
            {#each $store as item}
                <label class="checkbox-label">
                    <input
                        type="checkbox"
                        checked={item.checked}
                        on:change={() => toggleItem(item.id)}
                    />
                    {item.label}
                </label>
            {/each}
        </div>
    {/if}
</div>

