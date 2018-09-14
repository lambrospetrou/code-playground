import { createApp } from './app'
import { setupMeiosis } from './meiosis'

setupMeiosis(createApp, document.body.querySelector('#app') as HTMLElement)
