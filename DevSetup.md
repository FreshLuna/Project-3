---
title: DevSetup
layout: template
filename: DevSetup.md
--- 

# Project 3
## Installing Dependencies 

* ### [VsCode](https://code.visualstudio.com/)  (recommended)

  _Recommended extensions:_
  _[svelte-vscode](https://marketplace.visualstudio.com/items?itemName=svelte.svelte-vscode) for syntax_ 
  _[Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) for git integration_

  **I'm recommending using VsCode for following reasons:**  
  1. Easier switch between java and our webstack (typescript, html and css).  
  2. More consistency between groups when using the same "IDE" with the same base extensions.  
  3. Less confusion following the rest of the tutorial.  
  

* ### [node.js](https://nodejs.org/en/download)
  Node.js is a runtime environment and is required when developing in Svelte.  
  Node.js comes with the npm (node packet manager) installed by default.
  Npm makes it way easier to install dependencies, update packages and so on.

## Setting up the coding environment
After fetching the code from GitHub to VsCode, open the console and run:
```sh
npm install # for installing the packages
npm run dev # quick for running the project
npm run build # slow to create publishable optimised build
```
