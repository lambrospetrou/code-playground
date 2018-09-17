/**
 * Meiosis setup: https://meiosis.js.org/
 */

import * as m from 'mithril'
import * as ms from 'mithril/stream'

class AppModel {
  counter: number = 0
}

const createActions = (update: UpdateStream) => ({
  inc: (value: number) => update(model => ({ counter: model.counter + value }))
})

type ModelUpdateFunction = (model: AppModel) => AppModel
type UpdateStream = ms.Stream<ModelUpdateFunction>

interface MeiosisApp {
  /**
   * @returns the inital model of the app
   */
  model: () => AppModel

  /**
   * Called after every change in the model with the new model.
   * @param model The new model from which the new view will be generated.
   * @returns The new view that will be passed to the `render` method.
   */
  view: (model: AppModel) => m.Vnode

  /**
   * @param el The element the app is rendered to in the DOM
   * @param v The generated view node that will be put into the DOM
   */
  render: (el: Element, v: m.Vnode) => void
}

const createApp = (update: UpdateStream): MeiosisApp => {
  const actions = createActions(update)
  return {
    model: () => ({ counter: 0 }),
    view: (model: AppModel) =>
      m('div', [
        m('p', model.counter),
        m('button', { onclick: () => actions.inc(1) }, 'plus'),
        m('button', { onclick: () => actions.inc(-1) }, 'minus')
      ]),
    render: (container: Element, v: m.Vnode) => m.render(container, v)
  }
}

const setupMeiosis = (
  createApp: (s: UpdateStream) => MeiosisApp,
  container: Element
) => {
  const update = ms<ModelUpdateFunction>()
  const modelUpdate = (model: AppModel, fn: ModelUpdateFunction) => fn(model)

  const app = createApp(update)

  const models = ms.scan(modelUpdate, app.model(), update)
  models.map(model => app.render(container, app.view(model)))
}

setupMeiosis(createApp, document.body.querySelector('#app') as Element)
