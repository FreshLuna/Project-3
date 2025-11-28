<script lang="ts">
    //import { clear } from "console";
    import { onDestroy, onMount } from "svelte";
    import type { Writable } from "svelte/store";

    export let store: Writable<{ id: number; label: string; checked: boolean }[]>;
    export let label: string;

    let open = false;
    let dropdownRef: HTMLElement;

    function toggleItem(id: number) {
        store.update((list) =>
            list.map((item) =>
                item.id === id // please please please make this a real if
                    ? { ...item, checked: !item.checked }
                    : item
            )
        );
    }

    // script: clear all checkboxes
    function clearAll() {
        store.update((list) => list.map((item) => ({ ...item, checked: false })));
    }

    // script: close dropdown when clicking outisde
    function closeDropdownOnClickOutside(event: MouseEvent) {
        if (dropdownRef && !dropdownRef.contains(event.target as Node)) {
            open = false;
        }
    }

    // // event listeners for closing dropdowns
    onMount(() => {
        if (typeof document !== 'undefined') {
        document.addEventListener('click', closeDropdownOnClickOutside);
        }
    });

    onDestroy(() => {
        if (typeof document !== 'undefined') {
            document.removeEventListener('click', closeDropdownOnClickOutside);
        }
    });

    // onMount();

    // onDestroy(() => {
    //     document.removeEventListener("click", closeDropdownOnClickOutside)
    // });
</script>

<div class="dropdown" bind:this={dropdownRef}>
    <button type="button" class="dropdownButton" on:click={() => (open = !open)}>
        {label}
    </button>

    {#if open}
        <div class="menu">
            <button type="button" class="clear-btn" on:click={clearAll}>
                Fjern filtre
            </button>


            {#each $store as item}
                <label style="display: block;">
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

<style>
    .dropdownButton {
        background: #6e479b;
        border: 0.2rem solid #6e479b;
        color: white;
    }
    .dropdownButton:hover {
        background: white;
        border: 0.2rem solid #6e479b;
        color: #6e479b;
    }
    .dropdown {
        position: relative;
        display: inline-block;
        /* height: 40px; */
        display: flex;
        /* align-items: center; */
    }
    .menu {
        position: absolute;
        background: white;
        border: 0.2rem solid #6e479b;
        padding: 0.5rem;
        z-index: 10;
        min-width: 200px;
        top: 92%;
    }
    .clear-btn {
        display: block;
        margin-bottom: 0.5rem;
        font-size: 0.85rem;
        cursor: pointer;
        border: 0.2rem solid #6e479b;
        background: none;
        padding: 0;
        background: #6e479b;
        color: white;
        padding: 0.2rem;
    }
    .clear-btn:hover {
        background: white;
        color: #6e479b;
        border: 0.2rem solid #6e479b;
    }
</style>
