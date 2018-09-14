/**
 * Meiosis setup: https://meiosis.js.org/
 */

import * as ms from 'mithril/stream'

// Only for using Meiosis Tracer in development.
import { trace } from 'meiosis'
// @ts-ignore
import * as meiosisTracer from 'meiosis-tracer'
import {
  UpdateStream,
  ModelUpdateFunction,
  MeiosisApp,
  AppModel
} from './models'

export const setupMeiosis = (
  createApp: (s: UpdateStream) => MeiosisApp,
  container: HTMLElement
) => {
  const update = ms<ModelUpdateFunction>()
  const modelUpdate = (model: AppModel, fn: ModelUpdateFunction) => fn(model)

  const app = createApp(update)

  const models = ms.scan(modelUpdate, app.model(), update)
  models.map(model => app.render(container, app.view(model)))

  // Only for using Meiosis Tracer in development.
  trace({ update, dataStreams: [models] })
  meiosisTracer({ selector: '#tracer' })
}
