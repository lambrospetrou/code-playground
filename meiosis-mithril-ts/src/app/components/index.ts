import * as m from 'mithril'

import { AppModel, Actions } from '../models'

interface AttrsAppModel {
  model: AppModel
  [key: string]: any
}

interface AttrsActions {
  actions: Actions
  [key: string]: any
}

interface AttrsCommon extends AttrsActions, AttrsAppModel {}

export const Layout = {
  view (v) {
    return m('main.layout', [
      m('h1', ['Meiosis - Demo']),
      m('section', v.children)
    ])
  }
} as m.Component<{}, any>

export const Counter = {
  view (v) {
    const { actions, model } = v.attrs
    return m('.counter', [
      m('button', { onclick: () => actions.inc(1) }, 'Inc'),
      m('button', { onclick: () => actions.inc(0) }, 'Same'),
      m('strong', {}, `${model.counter}`)
    ])
  }
} as m.Component<AttrsCommon, any>

export const RandomNum = {
  view (v) {
    const { actions, model } = v.attrs
    return m('.random', [
      m('button', { onclick: () => actions.randomize('num') }, 'Randomize'),
      m('strong', {}, `${model.randomText}`)
    ])
  }
} as m.Component<AttrsCommon, any>

export const App = {
  view: (actions: Actions, model: AppModel) =>
    m(Layout, {}, [
      m(Counter, { actions, model }),
      m(RandomNum, { actions, model })
    ])
}
