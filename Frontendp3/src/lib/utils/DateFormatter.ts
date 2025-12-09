export function dateFormatter(input?: string | number): string {
    if (!input) return 'Invalid date';

    const date = input.toString();

    // Make sure the string is long enough
    if (date.length < 12) return 'Invalid date';

    const year = date.slice(0, 4);    
    const month = date.slice(4, 6);   
    const day = date.slice(6, 8);     
    const hour = date.slice(8, 10);   
    const minute = date.slice(10, 12);

    return `${day}/${month}/${year} ${hour}:${minute}`;
}