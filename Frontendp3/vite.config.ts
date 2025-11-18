import fs from 'fs';
import { defineConfig } from 'vite';
import { sveltekit } from '@sveltejs/kit/vite';

export default defineConfig({
  plugins: [sveltekit()],
  server: {
    https: {
      key: fs.readFileSync('./vite.key'),
      cert: fs.readFileSync('./vite.crt'),
      minVersion: 'TLSv1.3'
    },
    port: 5173,
    host: true,
    proxy: {
      '/server': {
        target: 'https://localhost:8443',
        changeOrigin: true,
        secure: true, 
      }
    }
  }
});

