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
<style>
    .dropdownButton {
        background: var(--color-primary-purple);
        border: 0.2rem solid var(--color-primary-purple);
        color: var(--color-white);
        padding: 0.5rem 1rem;
        border-radius: 4px;
        font-size: 1rem;
        font-weight: 600;
        cursor: pointer;
        transition: all 200ms ease;
    }
    .dropdownButton:hover {
        background: var(--color-white);
        border: 0.2rem solid var(--color-primary-purple);
        color: var(--color-primary-purple);
    }
    .dropdownButton.active {
        background: var(--color-white);
        border: 0.2rem solid var(--color-primary-purple);
        color: var(--color-primary-purple);
    }

    .dropdown {
        position: relative;
        display: inline-block;
    }
    .menu {
        position: absolute;
        background: var(--color-white);
        border: 0.2rem solid var(--color-primary-purple);
        border-radius: 4px;
        padding: 0.5rem;

        z-index: 100;
        min-width: 200px;

        top: calc(100% + 4px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .checkbox-label {
        display: block;
        padding: 0.4rem 0.5rem;
        cursor: pointer;
        transition: background-color 150ms ease;
        border-radius: 2px;
    }

    .checkbox-label:hover {
        background-color: #f5f5f5;
    }

    .checkbox-label input[type="checkbox"] {
        margin-right: 0.5rem;
        cursor: pointer;
    }
    .clear-btn {
        width: 100%;
        display: block;
        margin-bottom: 0.5rem;

        font-size: 0.9rem;
        cursor: pointer;
        border: 0.2rem solid var(--color-primary-purple);

        background: var(--color-primary-purple);
        color: var(--color-white);

        padding: 0.4rem;
        border-radius: 4px;
        transition: all 200ms ease;
    }
    .clear-btn:hover {
        background: var(--color-white);
        color: var(--color-primary-purple);
        border: 0.2rem solid var(--color-primary-purple);
    }
</style>
